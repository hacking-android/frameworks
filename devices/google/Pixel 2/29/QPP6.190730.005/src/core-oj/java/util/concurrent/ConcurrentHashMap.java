/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Helpers;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import sun.misc.Unsafe;

public class ConcurrentHashMap<K, V>
extends AbstractMap<K, V>
implements ConcurrentMap<K, V>,
Serializable {
    private static final int ABASE;
    private static final int ASHIFT;
    private static final long BASECOUNT;
    private static final long CELLSBUSY;
    private static final long CELLVALUE;
    private static final int DEFAULT_CAPACITY = 16;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    static final int HASH_BITS = Integer.MAX_VALUE;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_ARRAY_SIZE = 2147483639;
    private static final int MAX_RESIZERS = 65535;
    private static final int MIN_TRANSFER_STRIDE = 16;
    static final int MIN_TREEIFY_CAPACITY = 64;
    static final int MOVED = -1;
    static final int NCPU;
    static final int RESERVED = -3;
    private static final int RESIZE_STAMP_BITS = 16;
    private static final int RESIZE_STAMP_SHIFT = 16;
    private static final long SIZECTL;
    private static final long TRANSFERINDEX;
    static final int TREEBIN = -2;
    static final int TREEIFY_THRESHOLD = 8;
    private static final Unsafe U;
    static final int UNTREEIFY_THRESHOLD = 6;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = 7249069246763182397L;
    private volatile transient long baseCount;
    private volatile transient int cellsBusy;
    private volatile transient CounterCell[] counterCells;
    private transient EntrySetView<K, V> entrySet;
    private transient KeySetView<K, V> keySet;
    private volatile transient Node<K, V>[] nextTable;
    private volatile transient int sizeCtl;
    volatile transient Node<K, V>[] table;
    private volatile transient int transferIndex;
    private transient ValuesView<K, V> values;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        NCPU = Runtime.getRuntime().availableProcessors();
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("segments", Segment[].class), new ObjectStreamField("segmentMask", Integer.TYPE), new ObjectStreamField("segmentShift", Integer.TYPE)};
        U = Unsafe.getUnsafe();
        try {
            SIZECTL = U.objectFieldOffset(ConcurrentHashMap.class.getDeclaredField("sizeCtl"));
            TRANSFERINDEX = U.objectFieldOffset(ConcurrentHashMap.class.getDeclaredField("transferIndex"));
            BASECOUNT = U.objectFieldOffset(ConcurrentHashMap.class.getDeclaredField("baseCount"));
            CELLSBUSY = U.objectFieldOffset(ConcurrentHashMap.class.getDeclaredField("cellsBusy"));
            CELLVALUE = U.objectFieldOffset(CounterCell.class.getDeclaredField("value"));
            ABASE = U.arrayBaseOffset(Node[].class);
            int n = U.arrayIndexScale(Node[].class);
            if ((n - 1 & n) == 0) {
                ASHIFT = 31 - Integer.numberOfLeadingZeros(n);
                return;
            }
            Error error = new Error("array index scale not a power of two");
            throw error;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public ConcurrentHashMap() {
    }

    public ConcurrentHashMap(int n) {
        if (n >= 0) {
            n = n >= 536870912 ? 1073741824 : ConcurrentHashMap.tableSizeFor((n >>> 1) + n + 1);
            this.sizeCtl = n;
            return;
        }
        throw new IllegalArgumentException();
    }

    public ConcurrentHashMap(int n, float f) {
        this(n, f, 1);
    }

    public ConcurrentHashMap(int n, float f, int n2) {
        if (f > 0.0f && n >= 0 && n2 > 0) {
            long l;
            int n3 = n;
            if (n < n2) {
                n3 = n2;
            }
            n = (l = (long)((double)((float)n3 / f) + 1.0)) >= 0x40000000L ? 1073741824 : ConcurrentHashMap.tableSizeFor((int)l);
            this.sizeCtl = n;
            return;
        }
        throw new IllegalArgumentException();
    }

    public ConcurrentHashMap(Map<? extends K, ? extends V> map) {
        this.sizeCtl = 16;
        this.putAll(map);
    }

    private final void addCount(long l, int n) {
        boolean bl;
        block12 : {
            Node<K, V>[] arrnode;
            Object object;
            long l2;
            int n2;
            block11 : {
                boolean bl2;
                long l3;
                block10 : {
                    object = this.counterCells;
                    if (object != null) break block10;
                    arrnode = U;
                    long l4 = BASECOUNT;
                    long l5 = this.baseCount;
                    l2 = l3 = l5 + l;
                    if (arrnode.compareAndSwapLong(this, l4, l5, l3)) break block11;
                }
                bl = bl2 = true;
                if (object == null) break block12;
                n2 = ((CounterCell[])object).length - 1;
                bl = bl2;
                if (n2 < 0) break block12;
                object = object[ThreadLocalRandom.getProbe() & n2];
                bl = bl2;
                if (object == null) break block12;
                arrnode = U;
                l3 = CELLVALUE;
                l2 = object.value;
                bl = bl2 = arrnode.compareAndSwapLong(object, l3, l2, l2 + l);
                if (!bl2) break block12;
                if (n <= 1) {
                    return;
                }
                l2 = this.sumCount();
            }
            if (n >= 0) {
                while (l2 >= (long)(n = this.sizeCtl) && (arrnode = this.table) != null && (n2 = arrnode.length) < 1073741824) {
                    n2 = ConcurrentHashMap.resizeStamp(n2);
                    if (n < 0) {
                        if (n >>> 16 != n2 || n == n2 + 1 || n == 65535 + n2 || (object = this.nextTable) == null || this.transferIndex <= 0) break;
                        if (U.compareAndSwapInt(this, SIZECTL, n, n + 1)) {
                            this.transfer(arrnode, (Node<K, V>[])object);
                        }
                    } else if (U.compareAndSwapInt(this, SIZECTL, n, (n2 << 16) + 2)) {
                        this.transfer(arrnode, null);
                    }
                    l2 = this.sumCount();
                }
            }
            return;
        }
        this.fullAddCount(l, bl);
    }

    static final <K, V> boolean casTabAt(Node<K, V>[] arrnode, int n, Node<K, V> node, Node<K, V> node2) {
        return U.compareAndSwapObject(arrnode, ((long)n << ASHIFT) + (long)ABASE, node, node2);
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

    private final void fullAddCount(long l, boolean bl) {
        int n;
        int n2 = n = ThreadLocalRandom.getProbe();
        if (n == 0) {
            ThreadLocalRandom.localInit();
            n2 = ThreadLocalRandom.getProbe();
            bl = true;
        }
        n = n2;
        n2 = 0;
        do {
            long l2;
            int n3;
            long l3;
            Object object;
            Object object2;
            if ((object = this.counterCells) != null && (n3 = ((CounterCell[])object).length) > 0) {
                boolean bl2;
                CounterCell counterCell = object[n3 - 1 & n];
                if (counterCell == null) {
                    if (this.cellsBusy == 0) {
                        object2 = new CounterCell(l);
                        if (this.cellsBusy == 0 && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                            block29 : {
                                int n4 = 0;
                                try {
                                    object = this.counterCells;
                                    n3 = n4;
                                    if (object == null) break block29;
                                }
                                catch (Throwable throwable) {
                                    this.cellsBusy = 0;
                                    throw throwable;
                                }
                                int n5 = ((Object)object).length;
                                n3 = n4;
                                if (n5 <= 0) break block29;
                                n5 = n5 - 1 & n;
                                n3 = n4;
                                if (object[n5] == null) {
                                    object[n5] = object2;
                                    n3 = 1;
                                }
                            }
                            this.cellsBusy = 0;
                            if (n3 == 0) continue;
                            break;
                        }
                    }
                    n2 = 0;
                    bl2 = bl;
                } else if (!bl) {
                    bl2 = true;
                } else {
                    object2 = U;
                    l2 = CELLVALUE;
                    l3 = counterCell.value;
                    if (object2.compareAndSwapLong(counterCell, l2, l3, l3 + l)) break;
                    if (this.counterCells == object && n3 < NCPU) {
                        if (n2 == 0) {
                            n2 = 1;
                            bl2 = bl;
                        } else if (this.cellsBusy == 0) {
                            bl2 = bl;
                            if (U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                                block30 : {
                                    if (this.counterCells != object) break block30;
                                    object2 = new CounterCell[n3 << 1];
                                    for (n2 = 0; n2 < n3; ++n2) {
                                        object2[n2] = object[n2];
                                    }
                                    try {
                                        this.counterCells = object2;
                                    }
                                    catch (Throwable throwable) {
                                        throw throwable;
                                    }
                                    finally {
                                        this.cellsBusy = 0;
                                    }
                                }
                                n2 = 0;
                                continue;
                            }
                        } else {
                            bl2 = bl;
                        }
                    } else {
                        n2 = 0;
                        bl2 = bl;
                    }
                }
                n = ThreadLocalRandom.advanceProbe(n);
                bl = bl2;
                continue;
            }
            if (this.cellsBusy == 0 && this.counterCells == object && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                block31 : {
                    n3 = 0;
                    try {
                        if (this.counterCells != object) break block31;
                        object = new CounterCell[2];
                        object2 = new CounterCell(l);
                        object[n & 1] = object2;
                    }
                    catch (Throwable throwable) {
                        this.cellsBusy = 0;
                        throw throwable;
                    }
                    this.counterCells = object;
                    n3 = 1;
                }
                this.cellsBusy = 0;
                if (n3 == 0) continue;
                break;
            }
            object = U;
            l2 = BASECOUNT;
            l3 = this.baseCount;
            if (((Unsafe)object).compareAndSwapLong(this, l2, l3, l3 + l)) break;
        } while (true);
    }

    private final Node<K, V>[] initTable() {
        Node<K, V>[] arrnode;
        block8 : {
            Node<K, V>[] arrnode2;
            int n;
            block10 : {
                int n2;
                block9 : {
                    do {
                        arrnode2 = arrnode = this.table;
                        if (arrnode != null) {
                            arrnode = arrnode2;
                            if (arrnode2.length != 0) break block8;
                        }
                        n2 = n = this.sizeCtl;
                        if (n < 0) {
                            Thread.yield();
                            continue;
                        }
                        if (U.compareAndSwapInt(this, SIZECTL, n2, -1)) break;
                    } while (true);
                    try {
                        arrnode = arrnode2 = this.table;
                        if (arrnode2 == null) break block9;
                        arrnode2 = arrnode;
                        n = n2;
                    }
                    catch (Throwable throwable) {
                        this.sizeCtl = n2;
                        throw throwable;
                    }
                    if (arrnode.length != 0) break block10;
                }
                n = n2 > 0 ? n2 : 16;
                arrnode2 = arrnode = new Node[n];
                this.table = arrnode;
                n -= n >>> 2;
            }
            this.sizeCtl = n;
            arrnode = arrnode2;
        }
        return arrnode;
    }

    public static <K> KeySetView<K, Boolean> newKeySet() {
        return new KeySetView<K, Boolean>(new ConcurrentHashMap<K, V>(), Boolean.TRUE);
    }

    public static <K> KeySetView<K, Boolean> newKeySet(int n) {
        return new KeySetView<K, Boolean>(new ConcurrentHashMap<K, V>(n), Boolean.TRUE);
    }

    private void readObject(ObjectInputStream node) throws IOException, ClassNotFoundException {
        TreeNode treeNode;
        TreeNode treeNode2;
        this.sizeCtl = -1;
        ((ObjectInputStream)((Object)node)).defaultReadObject();
        long l = 0L;
        Node<Object, Object> node2 = null;
        do {
            treeNode = ((ObjectInputStream)((Object)node)).readObject();
            treeNode2 = ((ObjectInputStream)((Object)node)).readObject();
            if (treeNode == null || treeNode2 == null) break;
            node2 = new Node<Object, Object>(ConcurrentHashMap.spread(((Object)treeNode).hashCode()), treeNode, treeNode2, node2);
            ++l;
        } while (true);
        if (l == 0L) {
            this.sizeCtl = 0;
        } else {
            int n;
            int n2;
            if (l >= 0x20000000L) {
                n2 = 1073741824;
            } else {
                n = (int)l;
                n2 = ConcurrentHashMap.tableSizeFor((n >>> 1) + n + 1);
            }
            Node[] arrnode = new Node[n2];
            n = n2 - 1;
            long l2 = 0L;
            node = node2;
            long l3 = l;
            while (node != null) {
                boolean bl;
                Node node3 = node.next;
                int n3 = node.hash;
                int n4 = n3 & n;
                Node<K, V> node4 = ConcurrentHashMap.tabAt(arrnode, n4);
                if (node4 == null) {
                    bl = true;
                } else {
                    treeNode = node.key;
                    if (node4.hash < 0) {
                        l = l2;
                        if (((TreeBin)node4).putTreeVal(n3, treeNode, node.val) == null) {
                            l = l2 + 1L;
                        }
                        bl = false;
                        l2 = l;
                    } else {
                        boolean bl2 = true;
                        int n5 = 0;
                        node2 = node4;
                        do {
                            bl = bl2;
                            if (node2 == null) break;
                            if (node2.hash == n3 && ((treeNode2 = node2.key) == treeNode || treeNode2 != null && ((Object)treeNode).equals(treeNode2))) {
                                bl = false;
                                break;
                            }
                            ++n5;
                            node2 = node2.next;
                        } while (true);
                        if (bl && n5 >= 8) {
                            bl = false;
                            ++l2;
                            node.next = node4;
                            treeNode = null;
                            node2 = node;
                            TreeNode treeNode3 = null;
                            while (node2 != null) {
                                treeNode2 = new TreeNode(node2.hash, node2.key, node2.val, null, null);
                                treeNode2.prev = treeNode3;
                                if (treeNode3 == null) {
                                    treeNode = treeNode2;
                                } else {
                                    treeNode3.next = treeNode2;
                                }
                                node2 = node2.next;
                                treeNode3 = treeNode2;
                            }
                            ConcurrentHashMap.setTabAt(arrnode, n4, new TreeBin(treeNode));
                        }
                    }
                }
                if (bl) {
                    ++l2;
                    node.next = node4;
                    ConcurrentHashMap.setTabAt(arrnode, n4, node);
                }
                node = node3;
            }
            this.table = arrnode;
            this.sizeCtl = n2 - (n2 >>> 2);
            this.baseCount = l2;
        }
    }

    static final int resizeStamp(int n) {
        return Integer.numberOfLeadingZeros(n) | 32768;
    }

    static final <K, V> void setTabAt(Node<K, V>[] arrnode, int n, Node<K, V> node) {
        U.putObjectVolatile(arrnode, ((long)n << ASHIFT) + (long)ABASE, node);
    }

    static final int spread(int n) {
        return (n >>> 16 ^ n) & Integer.MAX_VALUE;
    }

    static final <K, V> Node<K, V> tabAt(Node<K, V>[] arrnode, int n) {
        return (Node)U.getObjectVolatile(arrnode, ((long)n << ASHIFT) + (long)ABASE);
    }

    private static final int tableSizeFor(int n) {
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

    /*
     * Exception decompiling
     */
    private final void transfer(Node<K, V>[] var1_1, Node<K, V>[] var2_10) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[TRYBLOCK]], but top level block is 10[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void treeifyBin(Node<K, V>[] arrnode, int n) {
        if (arrnode == null) return;
        int n2 = arrnode.length;
        if (n2 < 64) {
            this.tryPresize(n2 << 1);
            return;
        }
        TreeBin treeBin = ConcurrentHashMap.tabAt(arrnode, n);
        if (treeBin == null) return;
        if (treeBin.hash < 0) return;
        synchronized (treeBin) {
            if (ConcurrentHashMap.tabAt(arrnode, n) != treeBin) return;
            TreeNode treeNode = null;
            TreeNode treeNode2 = null;
            Node node = treeBin;
            do {
                if (node == null) {
                    node = new Node(treeNode);
                    ConcurrentHashMap.setTabAt(arrnode, n, node);
                    return;
                }
                TreeNode treeNode3 = new TreeNode(node.hash, node.key, node.val, null, null);
                treeNode3.prev = treeNode2;
                if (treeNode2 == null) {
                    treeNode = treeNode3;
                } else {
                    treeNode2.next = treeNode3;
                }
                treeNode2 = treeNode3;
                node = node.next;
            } while (true);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final void tryPresize(int var1_1) {
        var1_1 = var1_1 >= 536870912 ? 1073741824 : ConcurrentHashMap.tableSizeFor((var1_1 >>> 1) + var1_1 + 1);
        do lbl-1000: // 5 sources:
        {
            var3_3 = var2_2 = this.sizeCtl;
            if (var2_2 < 0) return;
            var4_4 = this.table;
            if (var4_4 != null && (var2_2 = var4_4.length) != 0) {
                if (var1_1 <= var3_3) return;
                if (var2_2 >= 1073741824) {
                    return;
                }
                if (var4_4 != this.table || !ConcurrentHashMap.U.compareAndSwapInt(this, ConcurrentHashMap.SIZECTL, var3_3, ((var2_2 = ConcurrentHashMap.resizeStamp(var2_2)) << 16) + 2)) continue;
                this.transfer(var4_4, null);
                continue;
            }
            var5_6 = var3_3 > var1_1 ? var3_3 : var1_1;
            if (!ConcurrentHashMap.U.compareAndSwapInt(this, ConcurrentHashMap.SIZECTL, var3_3, -1)) continue;
            var2_2 = var3_3;
            if (this.table != var4_4) break block5;
            this.table = new Node[var5_6];
            break;
        } while (true);
        catch (Throwable var4_5) {
            this.sizeCtl = var3_3;
            throw var4_5;
        }
        {
            block5 : {
                var2_2 = var5_6 - (var5_6 >>> 2);
            }
            this.sizeCtl = var2_2;
            ** while (true)
        }
    }

    static <K, V> Node<K, V> untreeify(Node<K, V> node) {
        Node node2 = null;
        Node node3 = null;
        while (node != null) {
            Node node4 = new Node(node.hash, node.key, node.val, null);
            if (node3 == null) {
                node2 = node4;
            } else {
                node3.next = node4;
            }
            node = node.next;
            node3 = node4;
        }
        return node2;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n;
        int n2 = 0;
        for (n = 1; n < 16; n <<= 1) {
            ++n2;
        }
        Object object = new Segment[16];
        for (int i = 0; i < ((Segment[])object).length; ++i) {
            object[i] = new Segment(0.75f);
        }
        Object object2 = objectOutputStream.putFields();
        ((ObjectOutputStream.PutField)object2).put("segments", object);
        ((ObjectOutputStream.PutField)object2).put("segmentShift", 32 - n2);
        ((ObjectOutputStream.PutField)object2).put("segmentMask", n - 1);
        objectOutputStream.writeFields();
        object = this.table;
        if (object != null) {
            object = new Traverser((Node<K, V>[])object, ((Object[])object).length, 0, ((Object[])object).length);
            while ((object2 = ((Traverser)object).advance()) != null) {
                objectOutputStream.writeObject(((Node)object2).key);
                objectOutputStream.writeObject(((Node)object2).val);
            }
        }
        objectOutputStream.writeObject(null);
        objectOutputStream.writeObject(null);
    }

    final int batchFor(long l) {
        long l2;
        if (l != Long.MAX_VALUE && (l2 = this.sumCount()) > 1L && l2 >= l) {
            int n = ForkJoinPool.getCommonPoolParallelism() << 2;
            if (l > 0L && (l = l2 / l) < (long)n) {
                n = (int)l;
            }
            return n;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void clear() {
        long l = 0L;
        int n = 0;
        Node<K, V>[] arrnode = this.table;
        while (arrnode != null && n < arrnode.length) {
            Node<K, V> node = ConcurrentHashMap.tabAt(arrnode, n);
            if (node == null) {
                ++n;
                continue;
            }
            int n2 = node.hash;
            if (n2 == -1) {
                arrnode = this.helpTransfer(arrnode, node);
                n = 0;
                continue;
            }
            // MONITORENTER : node
            long l2 = l;
            int n3 = n;
            if (ConcurrentHashMap.tabAt(arrnode, n) == node) {
                Node<K, V> node2 = n2 >= 0 ? node : (node instanceof TreeBin ? ((TreeBin)node).first : null);
                while (node2 != null) {
                    --l;
                    node2 = node2.next;
                }
                ConcurrentHashMap.setTabAt(arrnode, n, null);
                n3 = n + 1;
                l2 = l;
            }
            // MONITOREXIT : node
            n = n3;
            l = l2;
        }
        if (l == 0L) return;
        this.addCount(l, -1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public V compute(K object, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (object == null) throw new NullPointerException();
        if (biFunction == null) throw new NullPointerException();
        int n = ConcurrentHashMap.spread(object.hashCode());
        int n2 = 0;
        Node<K, V>[] arrnode = this.table;
        int n3 = 0;
        Object object2 = null;
        do {
            Object object3;
            block43 : {
                block42 : {
                    int n4;
                    int n5;
                    int n6;
                    Object object4;
                    block38 : {
                        Object object5;
                        Node node;
                        block45 : {
                            block39 : {
                                int n7;
                                block44 : {
                                    block40 : {
                                        block41 : {
                                            object5 = object;
                                            if (arrnode == null || (n6 = arrnode.length) == 0) break block39;
                                            n5 = n6 - 1 & n;
                                            object3 = ConcurrentHashMap.tabAt(arrnode, n5);
                                            node = null;
                                            if (object3 != null) break block40;
                                            node = new ReservationNode();
                                            // MONITORENTER : node
                                            boolean bl = ConcurrentHashMap.casTabAt(arrnode, n5, null, node);
                                            n6 = n3;
                                            if (bl) {
                                                block37 : {
                                                    n2 = 1;
                                                    object4 = null;
                                                    try {
                                                        object2 = object3 = biFunction.apply(object5, null);
                                                        if (object3 == null) break block37;
                                                        n3 = 1;
                                                        object4 = new Node(n, object5, object2, null);
                                                    }
                                                    catch (Throwable throwable) {
                                                        ConcurrentHashMap.setTabAt(arrnode, n5, null);
                                                        throw throwable;
                                                    }
                                                }
                                                ConcurrentHashMap.setTabAt(arrnode, n5, object4);
                                                n6 = n3;
                                            }
                                            // MONITOREXIT : node
                                            if (n2 == 0) break block41;
                                            n3 = n6;
                                            break block42;
                                        }
                                        object3 = arrnode;
                                        n3 = n6;
                                        break block43;
                                    }
                                    n7 = object3.hash;
                                    if (n7 != -1) break block44;
                                    object3 = this.helpTransfer(arrnode, (Node<K, V>)object3);
                                    break block43;
                                }
                                // MONITORENTER : object3
                                TreeBin treeBin = ConcurrentHashMap.tabAt(arrnode, n5);
                                object4 = object2;
                                n6 = n2;
                                n4 = n3;
                                if (treeBin != object3) break block38;
                                if (n7 >= 0) break block45;
                                if (object3 instanceof TreeBin) {
                                    n6 = 1;
                                    treeBin = (TreeBin)object3;
                                    object4 = treeBin.root;
                                    object4 = object4 != null ? object4.findTreeNode(n, object5, null) : null;
                                    object2 = object4 == null ? node : object4.val;
                                    if ((object2 = biFunction.apply(object5, object2)) != null) {
                                        if (object4 != null) {
                                            object4.val = object2;
                                        } else {
                                            n3 = 1;
                                            treeBin.putTreeVal(n, object5, object2);
                                        }
                                    } else if (object4 != null) {
                                        n3 = n4 = -1;
                                        if (treeBin.removeTreeNode(object4)) {
                                            ConcurrentHashMap.setTabAt(arrnode, n5, ConcurrentHashMap.untreeify(treeBin.first));
                                            n3 = n4;
                                        }
                                    }
                                    object4 = object2;
                                    n4 = n3;
                                } else {
                                    if (object3 instanceof ReservationNode) {
                                        object = new IllegalStateException("Recursive update");
                                        throw object;
                                    }
                                    object4 = object2;
                                    n6 = n2;
                                    n4 = n3;
                                }
                                break block38;
                            }
                            object3 = this.initTable();
                            break block43;
                        }
                        object4 = object3;
                        n6 = 1;
                        object2 = null;
                        do {
                            block48 : {
                                block47 : {
                                    block46 : {
                                        if (object4.hash != n || (node = object4.key) != object5 && (node == null || !object5.equals(node))) break block46;
                                        if ((object5 = biFunction.apply(object5, object4.val)) != null) {
                                            object4.val = object5;
                                            object4 = object5;
                                        } else {
                                            n3 = -1;
                                            object4 = object4.next;
                                            if (object2 != null) {
                                                object2.next = object4;
                                            } else {
                                                ConcurrentHashMap.setTabAt(arrnode, n5, object4);
                                            }
                                            object4 = object5;
                                        }
                                        break block47;
                                    }
                                    object2 = object4;
                                    node = object4.next;
                                    object4 = node;
                                    if (node != null) break block48;
                                    node = biFunction.apply(object5, null);
                                    object4 = node;
                                    if (node != null) {
                                        if (object2.next != null) {
                                            object = new IllegalStateException("Recursive update");
                                            throw object;
                                        }
                                        n3 = 1;
                                        object2.next = object4 = new Node(n, object5, node, null);
                                        object4 = node;
                                    }
                                }
                                n4 = n3;
                                break;
                            }
                            ++n6;
                        } while (true);
                    }
                    // MONITOREXIT : object3
                    object2 = object4;
                    n2 = n6;
                    object3 = arrnode;
                    n3 = n4;
                    if (n6 == 0) break block43;
                    object2 = object4;
                    n2 = n6;
                    n3 = n4;
                    if (n6 >= 8) {
                        this.treeifyBin(arrnode, n5);
                        n3 = n4;
                        n2 = n6;
                        object2 = object4;
                    }
                }
                if (n3 == 0) return (V)object2;
                this.addCount(n3, n2);
                return (V)object2;
            }
            arrnode = object3;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public V computeIfAbsent(K var1_1, Function<? super K, ? extends V> var2_3) {
        if (var1_1 == null) throw new NullPointerException();
        if (var2_3 == null) throw new NullPointerException();
        var3_4 = ConcurrentHashMap.spread(var1_1.hashCode());
        var4_5 /* !! */  = null;
        var5_6 = 0;
        var6_7 = this.table;
        do {
            block30 : {
                block29 : {
                    block32 : {
                        block33 : {
                            block26 : {
                                block31 : {
                                    block27 : {
                                        block28 : {
                                            if (var6_7 == null || (var7_8 = var6_7.length) == 0) break block26;
                                            var8_9 = var7_8 - 1 & var3_4;
                                            var9_10 /* !! */  = ConcurrentHashMap.tabAt(var6_7, var8_9);
                                            if (var9_10 /* !! */  != null) break block27;
                                            var10_11 /* !! */  = new ReservationNode();
                                            // MONITORENTER : var10_11 /* !! */ 
                                            var11_12 = ConcurrentHashMap.casTabAt(var6_7, var8_9, null, var10_11 /* !! */ );
                                            if (var11_12) {
                                                block25 : {
                                                    var5_6 = 1;
                                                    var12_13 /* !! */  = null;
                                                    try {
                                                        var4_5 /* !! */  = var9_10 /* !! */  = var2_3.apply(var1_1);
                                                        if (var9_10 /* !! */  == null) break block25;
                                                        var12_13 /* !! */  = new Node(var3_4, var1_1, var4_5 /* !! */ , null);
                                                    }
                                                    catch (Throwable var1_2) {
                                                        ConcurrentHashMap.setTabAt(var6_7, var8_9, null);
                                                        throw var1_2;
                                                    }
                                                }
                                                ConcurrentHashMap.setTabAt(var6_7, var8_9, var12_13 /* !! */ );
                                            }
                                            // MONITOREXIT : var10_11 /* !! */ 
                                            if (var5_6 == 0) break block28;
                                            var7_8 = var5_6;
                                            break block29;
                                        }
                                        var9_10 /* !! */  = var6_7;
                                        break block30;
                                    }
                                    var13_14 = var9_10 /* !! */ .hash;
                                    if (var13_14 != -1) break block31;
                                    var9_10 /* !! */  = this.helpTransfer(var6_7, (Node<K, V>)var9_10 /* !! */ );
                                    break block30;
                                }
                                var14_15 = false;
                                var15_16 = false;
                                var16_17 = false;
                                // MONITORENTER : var9_10 /* !! */ 
                                var12_13 /* !! */  = var4_5 /* !! */ ;
                                var7_8 = var5_6;
                                var17_18 = var15_16;
                                if (ConcurrentHashMap.tabAt(var6_7, var8_9) != var9_10 /* !! */ ) break block32;
                                if (var13_14 >= 0) break block33;
                                if (var9_10 /* !! */  instanceof TreeBin) {
                                    var7_8 = 2;
                                    var18_19 = (TreeBin)var9_10 /* !! */ ;
                                    var12_13 /* !! */  = var18_19.root;
                                    if (var12_13 /* !! */  != null && (var12_13 /* !! */  = var12_13 /* !! */ .findTreeNode(var3_4, var1_1, null)) != null) {
                                        var12_13 /* !! */  = var12_13 /* !! */ .val;
                                        var17_18 = var14_15;
                                    } else {
                                        var12_13 /* !! */  = var4_5 /* !! */  = (var10_11 /* !! */  = var2_3.apply(var1_1));
                                        var17_18 = var14_15;
                                        if (var10_11 /* !! */  != null) {
                                            var17_18 = true;
                                            var18_19.putTreeVal(var3_4, var1_1, var4_5 /* !! */ );
                                            var12_13 /* !! */  = var4_5 /* !! */ ;
                                        }
                                    }
                                } else {
                                    if (var9_10 /* !! */  instanceof ReservationNode) {
                                        var1_1 = new IllegalStateException("Recursive update");
                                        throw var1_1;
                                    }
                                    var12_13 /* !! */  = var4_5 /* !! */ ;
                                    var7_8 = var5_6;
                                    var17_18 = var15_16;
                                }
                                break block32;
                            }
                            var9_10 /* !! */  = this.initTable();
                            break block30;
                        }
                        var7_8 = 1;
                        var12_13 /* !! */  = var9_10 /* !! */ ;
                        do {
                            var10_11 /* !! */  = var12_13 /* !! */ ;
                            if (var10_11 /* !! */ .hash == var3_4 && ((var12_13 /* !! */  = var10_11 /* !! */ .key) == var1_1 || var12_13 /* !! */  != null && var1_1.equals(var12_13 /* !! */ ))) {
                                var12_13 /* !! */  = var10_11 /* !! */ .val;
                                var17_18 = var16_17;
                                break;
                            }
                            var12_13 /* !! */  = var4_5 /* !! */  = var10_11 /* !! */ .next;
                            if (var4_5 /* !! */  == null) {
                                var18_19 = var2_3.apply(var1_1);
                                var12_13 /* !! */  = var4_5 /* !! */  = var18_19;
                                var17_18 = var16_17;
                                if (var18_19 == null) break;
                                if (var10_11 /* !! */ .next != null) {
                                    var1_1 = new IllegalStateException("Recursive update");
                                    throw var1_1;
                                }
                                var17_18 = true;
                                var10_11 /* !! */ .next = var12_13 /* !! */  = new Node(var3_4, var1_1, var4_5 /* !! */ , null);
                                var12_13 /* !! */  = var4_5 /* !! */ ;
                                break;
                            }
                            ++var7_8;
                        } while (true);
                    }
                    // MONITOREXIT : var9_10 /* !! */ 
                    var4_5 /* !! */  = var12_13 /* !! */ ;
                    var5_6 = var7_8;
                    var9_10 /* !! */  = var6_7;
                    if (var7_8 != 0) {
                        if (var7_8 >= 8) {
                            this.treeifyBin(var6_7, var8_9);
                        }
                        var4_5 /* !! */  = var12_13 /* !! */ ;
                        if (!var17_18) {
                            return (V)var12_13 /* !! */ ;
                        } else {
                            ** GOTO lbl112
                        }
                    }
                    break block30;
                }
                if (var4_5 /* !! */  == null) return (V)var4_5 /* !! */ ;
                this.addCount(1L, var7_8);
                return (V)var4_5 /* !! */ ;
            }
            var6_7 = var9_10 /* !! */ ;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public V computeIfPresent(K var1_1, BiFunction<? super K, ? super V, ? extends V> var2_2) {
        block27 : {
            if (var1_1 == null) throw new NullPointerException();
            if (var2_2 == null) throw new NullPointerException();
            var3_3 = ConcurrentHashMap.spread(var1_1.hashCode());
            var4_4 /* !! */  = null;
            var5_5 = 0;
            var6_6 = 0;
            var7_7 = this.table;
            do {
                block29 : {
                    block30 : {
                        block28 : {
                            if (var7_7 == null || (var8_8 = var7_7.length) == 0) break block28;
                            var9_9 = var8_8 - 1 & var3_3;
                            var10_10 = ConcurrentHashMap.tabAt(var7_7, var9_9);
                            if (var10_10 == null) {
                                var11_11 = var6_6;
                                break block27;
                            }
                            var12_12 = var10_10.hash;
                            if (var12_12 == -1) {
                                var7_7 = this.helpTransfer(var7_7, var10_10);
                                continue;
                            }
                            // MONITORENTER : var10_10
                            var13_13 /* !! */  = var4_4 /* !! */ ;
                            var8_8 = var5_5;
                            var11_11 = var6_6;
                            if (ConcurrentHashMap.tabAt(var7_7, var9_9) != var10_10) break block29;
                            var14_14 = null;
                            if (var12_12 >= 0) break block30;
                            if (var10_10 instanceof TreeBin) {
                                var11_11 = 2;
                                var14_14 = (TreeBin)var10_10;
                                var15_15 = var14_14.root;
                                var13_13 /* !! */  = var4_4 /* !! */ ;
                                var8_8 = var5_5;
                                if (var15_15 != null) {
                                    var15_15 = var15_15.findTreeNode(var3_3, var1_1, null);
                                    var13_13 /* !! */  = var4_4 /* !! */ ;
                                    var8_8 = var5_5;
                                    if (var15_15 != null) {
                                        var4_4 /* !! */  = var2_2.apply(var1_1, var15_15.val);
                                        if (var4_4 /* !! */  != null) {
                                            var15_15.val = var4_4 /* !! */ ;
                                            var13_13 /* !! */  = var4_4 /* !! */ ;
                                            var8_8 = var5_5;
                                        } else {
                                            var5_5 = -1;
                                            var13_13 /* !! */  = var4_4 /* !! */ ;
                                            var8_8 = var5_5;
                                            if (var14_14.removeTreeNode(var15_15)) {
                                                ConcurrentHashMap.setTabAt(var7_7, var9_9, ConcurrentHashMap.untreeify(var14_14.first));
                                                var8_8 = var5_5;
                                                var13_13 /* !! */  = var4_4 /* !! */ ;
                                                ** GOTO lbl90
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (var10_10 instanceof ReservationNode) {
                                    var1_1 = new IllegalStateException("Recursive update");
                                    throw var1_1;
                                }
                                var13_13 /* !! */  = var4_4 /* !! */ ;
                                var8_8 = var5_5;
                                var11_11 = var6_6;
                            }
                            break block29;
                        }
                        var7_7 = this.initTable();
                        continue;
                    }
                    var11_11 = 1;
                    var13_13 /* !! */  = var10_10;
                    do {
                        block33 : {
                            block32 : {
                                block31 : {
                                    if (var13_13 /* !! */ .hash != var3_3 || (var15_15 = var13_13 /* !! */ .key) != var1_1 && (var15_15 == null || !var1_1.equals(var15_15))) break block31;
                                    var4_4 /* !! */  = var2_2.apply(var1_1, var13_13 /* !! */ .val);
                                    if (var4_4 /* !! */  != null) {
                                        var13_13 /* !! */ .val = var4_4 /* !! */ ;
                                    } else {
                                        var5_5 = -1;
                                        var13_13 /* !! */  = var13_13 /* !! */ .next;
                                        if (var14_14 != null) {
                                            var14_14.next = var13_13 /* !! */ ;
                                        } else {
                                            ConcurrentHashMap.setTabAt(var7_7, var9_9, var13_13 /* !! */ );
                                        }
                                    }
                                    break block32;
                                }
                                var14_14 = var13_13 /* !! */ ;
                                var15_15 = var13_13 /* !! */ .next;
                                var13_13 /* !! */  = var15_15;
                                if (var15_15 != null) break block33;
                            }
                            var13_13 /* !! */  = var4_4 /* !! */ ;
                            var8_8 = var5_5;
                            break;
                        }
                        ++var11_11;
                    } while (true);
                }
                // MONITOREXIT : var10_10
                var4_4 /* !! */  = var13_13 /* !! */ ;
                var5_5 = var8_8;
                var6_6 = var11_11;
                if (var11_11 != 0) break;
            } while (true);
            var5_5 = var8_8;
            var4_4 /* !! */  = var13_13 /* !! */ ;
        }
        if (var5_5 == 0) return (V)var4_4 /* !! */ ;
        this.addCount(var5_5, var11_11);
        return (V)var4_4 /* !! */ ;
    }

    public boolean contains(Object object) {
        return this.containsValue(object);
    }

    @Override
    public boolean containsKey(Object object) {
        boolean bl = this.get(object) != null;
        return bl;
    }

    @Override
    public boolean containsValue(Object object) {
        if (object != null) {
            Object object2 = this.table;
            if (object2 != null) {
                Node node;
                object2 = new Traverser<K, V>((Node<K, V>[])object2, ((Node<K, V>[])object2).length, 0, ((Node<K, V>[])object2).length);
                while ((node = ((Traverser)object2).advance()) != null) {
                    node = node.val;
                    if (node != object && (node == null || !object.equals(node))) continue;
                    return true;
                }
            }
            return false;
        }
        throw new NullPointerException();
    }

    public Enumeration<V> elements() {
        Node<K, V>[] arrnode = this.table;
        int n = arrnode == null ? 0 : arrnode.length;
        return new ValueIterator<K, V>(arrnode, n, 0, n, this);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySetView<K, V> entrySetView = this.entrySet;
        if (entrySetView == null) {
            this.entrySet = entrySetView = new EntrySetView(this);
        }
        return entrySetView;
    }

    @Override
    public boolean equals(Object iterator) {
        if (iterator != this) {
            Node<K, V> node;
            if (!(iterator instanceof Map)) {
                return false;
            }
            iterator = (Map)((Object)iterator);
            Node<K, V>[] object2 = this.table;
            int n = object2 == null ? 0 : object2.length;
            Traverser<K, V> traverser = new Traverser<K, V>(object2, n, 0, n);
            while ((node = traverser.advance()) != null) {
                Object v = node.val;
                node = iterator.get(node.key);
                if (node != null && (node == v || ((Object)node).equals(v))) continue;
                return false;
            }
            for (Map.Entry entry : iterator.entrySet()) {
                Object v;
                traverser = entry.getKey();
                if (traverser != null && (v = entry.getValue()) != null && (traverser = this.get(traverser)) != null && (v == traverser || v.equals(traverser))) continue;
                return false;
            }
        }
        return true;
    }

    public void forEach(long l, BiConsumer<? super K, ? super V> biConsumer) {
        if (biConsumer != null) {
            new ForEachMappingTask<K, V>(null, this.batchFor(l), 0, 0, this.table, biConsumer).invoke();
            return;
        }
        throw new NullPointerException();
    }

    public <U> void forEach(long l, BiFunction<? super K, ? super V, ? extends U> biFunction, Consumer<? super U> consumer) {
        if (biFunction != null && consumer != null) {
            new ForEachTransformedMappingTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, biFunction, consumer).invoke();
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        if (biConsumer != null) {
            Object object = this.table;
            if (object != null) {
                Traverser<K, V> traverser = new Traverser<K, V>((Node<K, V>[])object, ((Node<K, V>[])object).length, 0, ((Node<K, V>[])object).length);
                while ((object = traverser.advance()) != null) {
                    biConsumer.accept(object.key, object.val);
                }
            }
            return;
        }
        throw new NullPointerException();
    }

    public void forEachEntry(long l, Consumer<? super Map.Entry<K, V>> consumer) {
        if (consumer != null) {
            new ForEachEntryTask<K, V>(null, this.batchFor(l), 0, 0, this.table, consumer).invoke();
            return;
        }
        throw new NullPointerException();
    }

    public <U> void forEachEntry(long l, Function<Map.Entry<K, V>, ? extends U> function, Consumer<? super U> consumer) {
        if (function != null && consumer != null) {
            new ForEachTransformedEntryTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, function, consumer).invoke();
            return;
        }
        throw new NullPointerException();
    }

    public void forEachKey(long l, Consumer<? super K> consumer) {
        if (consumer != null) {
            new ForEachKeyTask<K, V>(null, this.batchFor(l), 0, 0, this.table, consumer).invoke();
            return;
        }
        throw new NullPointerException();
    }

    public <U> void forEachKey(long l, Function<? super K, ? extends U> function, Consumer<? super U> consumer) {
        if (function != null && consumer != null) {
            new ForEachTransformedKeyTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, function, consumer).invoke();
            return;
        }
        throw new NullPointerException();
    }

    public void forEachValue(long l, Consumer<? super V> consumer) {
        if (consumer != null) {
            new ForEachValueTask<K, V>(null, this.batchFor(l), 0, 0, this.table, consumer).invoke();
            return;
        }
        throw new NullPointerException();
    }

    public <U> void forEachValue(long l, Function<? super V, ? extends U> function, Consumer<? super U> consumer) {
        if (function != null && consumer != null) {
            new ForEachTransformedValueTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, function, consumer).invoke();
            return;
        }
        throw new NullPointerException();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public V get(Object var1_1) {
        var2_2 = ConcurrentHashMap.spread(var1_1.hashCode());
        var3_3 = this.table;
        var4_14 = null;
        if (var3_3 == null) return null;
        var5_15 = var3_3.length;
        if (var5_15 <= 0) return null;
        var6_16 = var3_4 = ConcurrentHashMap.tabAt(var3_3, var5_15 - 1 & var2_2);
        if (var3_4 == null) return null;
        var5_15 = var6_16.hash;
        if (var5_15 == var2_2) {
            var4_14 = var6_16.key;
            if (var4_14 == var1_1) return var6_16.val;
            var3_5 = var6_16;
            if (var4_14 != null) {
                var3_6 = var6_16;
                if (var1_1.equals(var4_14)) {
                    return var6_16.val;
                }
            }
        } else {
            var3_7 = var6_16;
            if (var5_15 < 0) {
                var3_8 = var6_16.find(var2_2, var1_1);
                var1_1 = var4_14;
                if (var3_8 == null) return (V)var1_1;
                var1_1 = var3_8.val;
                return (V)var1_1;
            }
        }
        do lbl-1000: // 4 sources:
        {
            var3_10 = var3_9.next;
            var6_16 = var3_10;
            if (var3_10 == null) return null;
            var3_11 = var6_16;
            if (var6_16.hash != var2_2) ** GOTO lbl-1000
            var4_14 = var6_16.key;
            if (var4_14 == var1_1) return var6_16.val;
            var3_12 = var6_16;
            if (var4_14 == null) ** GOTO lbl-1000
            var3_13 = var6_16;
        } while (!var1_1.equals(var4_14));
        return var6_16.val;
    }

    @Override
    public V getOrDefault(Object object, V v) {
        block0 : {
            if ((object = this.get(object)) != null) break block0;
            object = v;
        }
        return (V)object;
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = 0;
        Object object = this.table;
        if (object != null) {
            object = new Traverser<K, V>((Node<K, V>[])object, ((Node<K, V>[])object).length, 0, ((Node<K, V>[])object).length);
            do {
                Node node = ((Traverser)object).advance();
                n = n2;
                if (node == null) break;
                n2 += node.key.hashCode() ^ node.val.hashCode();
            } while (true);
        }
        return n;
    }

    final Node<K, V>[] helpTransfer(Node<K, V>[] arrnode, Node<K, V> arrnode2) {
        if (arrnode != null && arrnode2 instanceof ForwardingNode && (arrnode2 = ((ForwardingNode)arrnode2).nextTable) != null) {
            int n;
            int n2 = ConcurrentHashMap.resizeStamp(arrnode.length);
            while (arrnode2 == this.nextTable && this.table == arrnode && (n = this.sizeCtl) < 0 && n >>> 16 == n2 && n != n2 + 1 && n != 65535 + n2 && this.transferIndex > 0) {
                if (!U.compareAndSwapInt(this, SIZECTL, n, n + 1)) continue;
                this.transfer(arrnode, arrnode2);
                break;
            }
            return arrnode2;
        }
        return this.table;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.sumCount() <= 0L;
        return bl;
    }

    @Override
    public Set<K> keySet() {
        KeySetView<K, Object> keySetView = this.keySet;
        if (keySetView == null) {
            this.keySet = keySetView = new KeySetView(this, null);
        }
        return keySetView;
    }

    public KeySetView<K, V> keySet(V v) {
        if (v != null) {
            return new KeySetView(this, v);
        }
        throw new NullPointerException();
    }

    public Enumeration<K> keys() {
        Node<K, V>[] arrnode = this.table;
        int n = arrnode == null ? 0 : arrnode.length;
        return new KeyIterator<K, V>(arrnode, n, 0, n, this);
    }

    public long mappingCount() {
        long l = this.sumCount();
        long l2 = 0L;
        if (l >= 0L) {
            l2 = l;
        }
        return l2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public V merge(K var1_1, V var2_10, BiFunction<? super V, ? super V, ? extends V> var3_11) {
        if (var1_1 == null) throw new NullPointerException();
        if (var2_15 == null) throw new NullPointerException();
        if (var3_16 == null) throw new NullPointerException();
        var4_17 = ConcurrentHashMap.spread(var1_1.hashCode());
        var5_18 = 0;
        var6_19 = this.table;
        var7_20 = 0;
        var8_21 = null;
        do {
            block46 : {
                block47 : {
                    block39 : {
                        block49 : {
                            block44 : {
                                block38 : {
                                    block48 : {
                                        block45 : {
                                            var9_40 = var1_1;
                                            if (var6_19 == null || (var10_42 = var6_19.length) == 0) break block44;
                                            var11_43 = var10_42 - 1 & var4_17;
                                            var12_44 = ConcurrentHashMap.tabAt(var6_19, var11_43);
                                            if (var12_44 != null) break block45;
                                            var10_42 = var5_18;
                                            var12_44 = var8_22;
                                            var13_46 = var6_19;
                                            var14_58 = var7_20;
                                            if (!ConcurrentHashMap.casTabAt(var6_19, var11_43, null, new Node<Object, void>(var4_17, var9_40, var2_15, null))) break block46;
                                            var5_18 = 1;
                                            var1_2 = var2_15;
                                            break block47;
                                        }
                                        var10_42 = var12_44.hash;
                                        if (var10_42 != -1) break block48;
                                        var13_47 = this.helpTransfer(var6_19, var12_44);
                                        var10_42 = var5_18;
                                        var12_44 = var8_22;
                                        var14_58 = var7_20;
                                        break block46;
                                    }
                                    // MONITORENTER : var12_44
                                    var13_48 = ConcurrentHashMap.tabAt(var6_19, var11_43);
                                    if (var13_48 != var12_44) break block39;
                                    if (var10_42 >= 0) break block49;
                                    try {
                                        if (!(var12_44 instanceof TreeBin)) break block38;
                                        var7_20 = 2;
                                        var15_65 = (TreeBin)var12_44;
                                        var8_32 = var15_65.root;
                                        if (var8_32 == null) {
                                            var13_52 = null;
                                        } else {
                                            var13_53 = var8_32.findTreeNode(var4_17, var9_40, null);
                                        }
                                        if (var13_54 == null) {
                                            var8_33 = var2_15;
                                        } else {
                                            var8_34 = var3_16.apply(var13_54.val, var2_15);
                                        }
                                        if (var8_35 == null) ** GOTO lbl61
                                        if (var13_54 != null) {
                                            var13_54.val = var8_35;
                                            break block39;
                                        }
                                        var5_18 = 1;
                                    }
                                    catch (Throwable var1_9) {
                                        throw var1_12;
                                    }
                                    try {
                                        var15_65.putTreeVal(var4_17, var9_40, var8_35);
                                        var5_18 = 1;
lbl61: // 1 sources:
                                        if (var13_54 == null) ** GOTO lbl146
                                        var5_18 = var10_42 = -1;
                                        if (var15_65.removeTreeNode(var13_54)) {
                                            var5_18 = var10_42;
                                            ConcurrentHashMap.setTabAt(var6_19, var11_43, ConcurrentHashMap.untreeify(var15_65.first));
                                        }
                                        var5_18 = -1;
                                    }
                                    catch (Throwable var1_7) {
                                        throw var1_12;
                                    }
                                }
                                if (var12_44 instanceof ReservationNode) {
                                    var1_8 = new IllegalStateException("Recursive update");
                                    throw var1_8;
                                }
                                break block39;
                            }
                            var13_57 = this.initTable();
                            var14_58 = var7_20;
                            var12_44 = var8_22;
                            var10_42 = var5_18;
                            break block46;
                        }
                        var8_23 = var12_44;
                        var7_20 = 1;
                        var13_49 = null;
                        do lbl-1000: // 2 sources:
                        {
                            block41 : {
                                var10_42 = var5_18;
                                if (var8_24.hash != var4_17) break block40;
                                var10_42 = var5_18;
                                var15_61 = var8_24.key;
                                if (var15_61 == var9_40) break block41;
                                if (var15_61 == null) break block40;
                                try {
                                    var16_66 = var9_40.equals(var15_61);
                                    if (!var16_66) break block40;
                                }
                                catch (Throwable var1_3) {
                                    throw var1_12;
                                }
                            }
                            var5_18 = var10_42 = var5_18;
                            var9_41 = var3_16.apply(var8_24.val, var2_15);
                            if (var9_41 == null) ** break block42
                            var5_18 = var10_42;
                            var8_24.val = var9_41;
                            var5_18 = var10_42;
                            var8_25 = var9_41;
                            break block39;
                            break;
                        } while (true);
                        catch (Throwable var1_5) {
                            throw var1_12;
                        }
                        {
                            block43 : {
                                block40 : {
                                    try {
                                        var8_26 = var8_24.next;
                                        if (var13_50 != null) {
                                            var13_50.next = var8_26;
                                        } else {
                                            ConcurrentHashMap.setTabAt(var6_19, var11_43, var8_26);
                                        }
                                        var5_18 = -1;
                                        var8_27 = var9_41;
                                        break block39;
                                    }
                                    catch (Throwable var1_4) {
                                        throw var1_12;
                                    }
                                }
                                var10_42 = var5_18;
                                var13_51 = var8_24;
                                var5_18 = var10_42;
                                var8_28 = var15_62 = var8_24.next;
                                if (var15_62 != null) break block43;
                                var10_42 = 1;
                                var8_29 = var2_15;
                                var5_18 = var10_42 = 1;
                                var15_63 = new Node<Object, void>(var4_17, var9_40, var8_29, null);
                                var5_18 = var10_42;
                                var13_51.next = var15_63;
                                var5_18 = var10_42;
                                break block39;
                            }
                            ++var7_20;
                            var5_18 = var10_42;
                            ** while (true)
                        }
                        catch (Throwable var1_6) {
                            throw var1_12;
                        }
                    }
                    var10_42 = var5_18;
                    var12_44 = var8_36;
                    var13_56 = var6_19;
                    var14_58 = var7_20;
                    if (var7_20 == 0) break block46;
                    if (var7_20 >= 8) {
                        this.treeifyBin(var6_19, var11_43);
                    }
                    var1_10 = var8_36;
                }
                if (var5_18 == 0) return var1_11;
                this.addCount(var5_18, var7_20);
                return var1_11;
            }
            var5_18 = var10_42;
            var8_38 = var12_44;
            var6_19 = var13_45;
            var7_20 = var14_58;
        } while (true);
    }

    @Override
    public V put(K k, V v) {
        return this.putVal(k, v, false);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> object) {
        this.tryPresize(object.size());
        for (Map.Entry entry : object.entrySet()) {
            this.putVal(entry.getKey(), entry.getValue(), false);
        }
    }

    @Override
    public V putIfAbsent(K k, V v) {
        return this.putVal(k, v, true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    final V putVal(K var1_1, V var2_2, boolean var3_3) {
        if (var1_1 == null) throw new NullPointerException();
        if (var2_2 == null) throw new NullPointerException();
        var4_4 = ConcurrentHashMap.spread(var1_1.hashCode());
        var5_5 = 0;
        var6_6 = this.table;
        do {
            block19 : {
                block20 : {
                    block22 : {
                        block23 : {
                            block17 : {
                                block21 : {
                                    block18 : {
                                        if (var6_6 == null || (var7_7 = var6_6.length) == 0) break block17;
                                        var8_8 = var7_7 - 1 & var4_4;
                                        var9_9 /* !! */  = ConcurrentHashMap.tabAt(var6_6, var8_8);
                                        if (var9_9 /* !! */  != null) break block18;
                                        var10_10 = var5_5;
                                        var9_9 /* !! */  = var6_6;
                                        if (!ConcurrentHashMap.casTabAt(var6_6, var8_8, null, new Node<Object, V>(var4_4, var1_1, var2_2, null))) break block19;
                                        var7_7 = var5_5;
                                        break block20;
                                    }
                                    var10_10 = var9_9 /* !! */ .hash;
                                    if (var10_10 != -1) break block21;
                                    var9_9 /* !! */  = this.helpTransfer(var6_6, (Node<K, V>)var9_9 /* !! */ );
                                    var10_10 = var5_5;
                                    break block19;
                                }
                                var11_11 /* !! */  = null;
                                var12_12 /* !! */  = null;
                                var13_13 = null;
                                // MONITORENTER : var9_9 /* !! */ 
                                var7_7 = var5_5;
                                var14_14 /* !! */  = var12_12 /* !! */ ;
                                if (ConcurrentHashMap.tabAt(var6_6, var8_8) != var9_9 /* !! */ ) break block22;
                                if (var10_10 >= 0) break block23;
                                if (var9_9 /* !! */  instanceof TreeBin) {
                                    var7_7 = 2;
                                    var12_12 /* !! */  = ((TreeBin)var9_9 /* !! */ ).putTreeVal(var4_4, var1_1, var2_2);
                                    var14_14 /* !! */  = var11_11 /* !! */ ;
                                    if (var12_12 /* !! */  != null) {
                                        var13_13 = var12_12 /* !! */ .val;
                                        var14_14 /* !! */  = var13_13;
                                        if (!var3_3) {
                                            var12_12 /* !! */ .val = var2_2;
                                            var14_14 /* !! */  = var13_13;
                                        }
                                    }
                                } else {
                                    if (var9_9 /* !! */  instanceof ReservationNode) {
                                        var1_1 = new IllegalStateException("Recursive update");
                                        throw var1_1;
                                    }
                                    var7_7 = var5_5;
                                    var14_14 /* !! */  = var12_12 /* !! */ ;
                                }
                                break block22;
                            }
                            var9_9 /* !! */  = this.initTable();
                            var10_10 = var5_5;
                            break block19;
                        }
                        var7_7 = 1;
                        var14_14 /* !! */  = var9_9 /* !! */ ;
                        do {
                            var11_11 /* !! */  = var14_14 /* !! */ ;
                            if (var11_11 /* !! */ .hash == var4_4 && ((var14_14 /* !! */  = var11_11 /* !! */ .key) == var1_1 || var14_14 /* !! */  != null && var1_1.equals(var14_14 /* !! */ ))) {
                                var13_13 = var11_11 /* !! */ .val;
                                var14_14 /* !! */  = var13_13;
                                if (var3_3) break;
                                var11_11 /* !! */ .val = var2_2;
                                var14_14 /* !! */  = var13_13;
                                break;
                            }
                            var14_14 /* !! */  = var12_12 /* !! */  = var11_11 /* !! */ .next;
                            if (var12_12 /* !! */  == null) {
                                var11_11 /* !! */ .next = var14_14 /* !! */  = new Node(var4_4, var1_1, var2_2, null);
                                var14_14 /* !! */  = var13_13;
                                break;
                            }
                            ++var7_7;
                        } while (true);
                    }
                    // MONITOREXIT : var9_9 /* !! */ 
                    var10_10 = var7_7;
                    var9_9 /* !! */  = var6_6;
                    if (var7_7 != 0) {
                        if (var7_7 >= 8) {
                            this.treeifyBin(var6_6, var8_8);
                        }
                        if (var14_14 /* !! */  != null) {
                            return (V)var14_14 /* !! */ ;
                        } else {
                            ** GOTO lbl81
                        }
                    }
                    break block19;
                }
                this.addCount(1L, var7_7);
                return null;
            }
            var5_5 = var10_10;
            var6_6 = var9_9 /* !! */ ;
        } while (true);
    }

    public <U> U reduce(long l, BiFunction<? super K, ? super V, ? extends U> biFunction, BiFunction<? super U, ? super U, ? extends U> biFunction2) {
        if (biFunction != null && biFunction2 != null) {
            return (U)new MapReduceMappingsTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, null, biFunction, biFunction2).invoke();
        }
        throw new NullPointerException();
    }

    public <U> U reduceEntries(long l, Function<Map.Entry<K, V>, ? extends U> function, BiFunction<? super U, ? super U, ? extends U> biFunction) {
        if (function != null && biFunction != null) {
            return (U)new MapReduceEntriesTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, null, function, biFunction).invoke();
        }
        throw new NullPointerException();
    }

    public Map.Entry<K, V> reduceEntries(long l, BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> biFunction) {
        if (biFunction != null) {
            return (Map.Entry)new ReduceEntriesTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, biFunction).invoke();
        }
        throw new NullPointerException();
    }

    public double reduceEntriesToDouble(long l, ToDoubleFunction<Map.Entry<K, V>> toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
        if (toDoubleFunction != null && doubleBinaryOperator != null) {
            return (Double)new MapReduceEntriesToDoubleTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toDoubleFunction, d, doubleBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    public int reduceEntriesToInt(long l, ToIntFunction<Map.Entry<K, V>> toIntFunction, int n, IntBinaryOperator intBinaryOperator) {
        if (toIntFunction != null && intBinaryOperator != null) {
            return (Integer)new MapReduceEntriesToIntTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toIntFunction, n, intBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    public long reduceEntriesToLong(long l, ToLongFunction<Map.Entry<K, V>> toLongFunction, long l2, LongBinaryOperator longBinaryOperator) {
        if (toLongFunction != null && longBinaryOperator != null) {
            return (Long)new MapReduceEntriesToLongTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toLongFunction, l2, longBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    public K reduceKeys(long l, BiFunction<? super K, ? super K, ? extends K> biFunction) {
        if (biFunction != null) {
            return (K)new ReduceKeysTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, biFunction).invoke();
        }
        throw new NullPointerException();
    }

    public <U> U reduceKeys(long l, Function<? super K, ? extends U> function, BiFunction<? super U, ? super U, ? extends U> biFunction) {
        if (function != null && biFunction != null) {
            return (U)new MapReduceKeysTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, null, function, biFunction).invoke();
        }
        throw new NullPointerException();
    }

    public double reduceKeysToDouble(long l, ToDoubleFunction<? super K> toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
        if (toDoubleFunction != null && doubleBinaryOperator != null) {
            return (Double)new MapReduceKeysToDoubleTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toDoubleFunction, d, doubleBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    public int reduceKeysToInt(long l, ToIntFunction<? super K> toIntFunction, int n, IntBinaryOperator intBinaryOperator) {
        if (toIntFunction != null && intBinaryOperator != null) {
            return (Integer)new MapReduceKeysToIntTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toIntFunction, n, intBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    public long reduceKeysToLong(long l, ToLongFunction<? super K> toLongFunction, long l2, LongBinaryOperator longBinaryOperator) {
        if (toLongFunction != null && longBinaryOperator != null) {
            return (Long)new MapReduceKeysToLongTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toLongFunction, l2, longBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    public double reduceToDouble(long l, ToDoubleBiFunction<? super K, ? super V> toDoubleBiFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
        if (toDoubleBiFunction != null && doubleBinaryOperator != null) {
            return (Double)new MapReduceMappingsToDoubleTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toDoubleBiFunction, d, doubleBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    public int reduceToInt(long l, ToIntBiFunction<? super K, ? super V> toIntBiFunction, int n, IntBinaryOperator intBinaryOperator) {
        if (toIntBiFunction != null && intBinaryOperator != null) {
            return (Integer)new MapReduceMappingsToIntTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toIntBiFunction, n, intBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    public long reduceToLong(long l, ToLongBiFunction<? super K, ? super V> toLongBiFunction, long l2, LongBinaryOperator longBinaryOperator) {
        if (toLongBiFunction != null && longBinaryOperator != null) {
            return (Long)new MapReduceMappingsToLongTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toLongBiFunction, l2, longBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    public V reduceValues(long l, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        if (biFunction != null) {
            return new ReduceValuesTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, biFunction).invoke();
        }
        throw new NullPointerException();
    }

    public <U> U reduceValues(long l, Function<? super V, ? extends U> function, BiFunction<? super U, ? super U, ? extends U> biFunction) {
        if (function != null && biFunction != null) {
            return (U)new MapReduceValuesTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, null, function, biFunction).invoke();
        }
        throw new NullPointerException();
    }

    public double reduceValuesToDouble(long l, ToDoubleFunction<? super V> toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
        if (toDoubleFunction != null && doubleBinaryOperator != null) {
            return (Double)new MapReduceValuesToDoubleTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toDoubleFunction, d, doubleBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    public int reduceValuesToInt(long l, ToIntFunction<? super V> toIntFunction, int n, IntBinaryOperator intBinaryOperator) {
        if (toIntFunction != null && intBinaryOperator != null) {
            return (Integer)new MapReduceValuesToIntTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toIntFunction, n, intBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    public long reduceValuesToLong(long l, ToLongFunction<? super V> toLongFunction, long l2, LongBinaryOperator longBinaryOperator) {
        if (toLongFunction != null && longBinaryOperator != null) {
            return (Long)new MapReduceValuesToLongTask<K, V>(null, this.batchFor(l), 0, 0, this.table, null, toLongFunction, l2, longBinaryOperator).invoke();
        }
        throw new NullPointerException();
    }

    @Override
    public V remove(Object object) {
        return this.replaceNode(object, null, null);
    }

    @Override
    public boolean remove(Object object, Object object2) {
        if (object != null) {
            boolean bl = object2 != null && this.replaceNode(object, null, object2) != null;
            return bl;
        }
        throw new NullPointerException();
    }

    boolean removeEntryIf(Predicate<? super Map.Entry<K, V>> predicate) {
        if (predicate != null) {
            boolean bl = false;
            boolean bl2 = false;
            Object object = this.table;
            if (object != null) {
                object = new Traverser<K, V>((Node<K, V>[])object, ((Node<K, V>[])object).length, 0, ((Node<K, V>[])object).length);
                do {
                    Node node = ((Traverser)object).advance();
                    bl = bl2;
                    if (node == null) break;
                    Object k = node.key;
                    node = node.val;
                    bl = bl2;
                    if (predicate.test(new AbstractMap.SimpleImmutableEntry(k, node))) {
                        bl = bl2;
                        if (this.replaceNode(k, null, node) != null) {
                            bl = true;
                        }
                    }
                    bl2 = bl;
                } while (true);
            }
            return bl;
        }
        throw new NullPointerException();
    }

    boolean removeValueIf(Predicate<? super V> predicate) {
        if (predicate != null) {
            boolean bl = false;
            boolean bl2 = false;
            Object object = this.table;
            if (object != null) {
                object = new Traverser<K, V>((Node<K, V>[])object, ((Node<K, V>[])object).length, 0, ((Node<K, V>[])object).length);
                do {
                    Node node = ((Traverser)object).advance();
                    bl = bl2;
                    if (node == null) break;
                    Object k = node.key;
                    node = node.val;
                    bl = bl2;
                    if (predicate.test(node)) {
                        bl = bl2;
                        if (this.replaceNode(k, null, node) != null) {
                            bl = true;
                        }
                    }
                    bl2 = bl;
                } while (true);
            }
            return bl;
        }
        throw new NullPointerException();
    }

    @Override
    public V replace(K k, V v) {
        if (k != null && v != null) {
            return this.replaceNode(k, v, null);
        }
        throw new NullPointerException();
    }

    @Override
    public boolean replace(K k, V v, V v2) {
        if (k != null && v != null && v2 != null) {
            boolean bl = this.replaceNode(k, v2, v) != null;
            return bl;
        }
        throw new NullPointerException();
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction != null) {
            Object object = this.table;
            if (object != null) {
                Node<K, V> node;
                Traverser<K, V> traverser = new Traverser<K, V>((Node<K, V>[])object, ((Node<K, V>[])object).length, 0, ((Node<K, V>[])object).length);
                block0 : while ((node = traverser.advance()) != null) {
                    object = node.val;
                    Object k = node.key;
                    while ((node = biFunction.apply(k, object)) != null) {
                        if (this.replaceNode(k, node, object) != null) continue block0;
                        node = this.get(k);
                        object = node;
                        if (node != null) continue;
                        continue block0;
                    }
                    throw new NullPointerException();
                }
            }
            return;
        }
        throw new NullPointerException();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    final V replaceNode(Object var1_1, V var2_2, Object var3_3) {
        var4_4 = ConcurrentHashMap.spread(var1_1.hashCode());
        var5_5 = this.table;
        do {
            block24 : {
                block23 : {
                    block25 : {
                        block26 : {
                            block27 : {
                                var6_6 = var1_1;
                                if (var5_5 == null) return null;
                                var7_7 = var5_5.length;
                                if (var7_7 == 0) return null;
                                var8_8 = var7_7 - 1 & var4_4;
                                var9_9 = ConcurrentHashMap.tabAt(var5_5, var8_8);
                                if (var9_9 == null) {
                                    return null;
                                }
                                var10_10 = var9_9.hash;
                                if (var10_10 == -1) {
                                    var5_5 = this.helpTransfer(var5_5, var9_9);
                                    continue;
                                }
                                var11_11 = null;
                                var12_12 /* !! */  = null;
                                var13_13 = null;
                                var14_14 = 0;
                                // MONITORENTER : var9_9
                                var15_15 = var12_12 /* !! */ ;
                                var7_7 = var14_14;
                                if (ConcurrentHashMap.tabAt(var5_5, var8_8) != var9_9) break block24;
                                if (var10_10 >= 0) break block25;
                                if (!(var9_9 instanceof TreeBin)) break block26;
                                var7_7 = 1;
                                var13_13 = var9_9;
                                var12_12 /* !! */  = var13_13.root;
                                var15_15 = var11_11;
                                if (var12_12 /* !! */  == null) break block24;
                                var12_12 /* !! */  = var12_12 /* !! */ .findTreeNode(var4_4, var6_6, null);
                                var15_15 = var11_11;
                                if (var12_12 /* !! */  == null) break block24;
                                var6_6 = var12_12 /* !! */ .val;
                                if (var3_3 == null || var3_3 == var6_6) break block27;
                                var15_15 = var11_11;
                                if (var6_6 == null) break block24;
                                var15_15 = var11_11;
                                if (!var3_3.equals(var6_6)) break block24;
                            }
                            if (var2_2 != null) {
                                var12_12 /* !! */ .val = var2_2;
                                var15_15 = var6_6;
                            } else {
                                var15_15 = var6_6;
                                if (var13_13.removeTreeNode(var12_12 /* !! */ )) {
                                    ConcurrentHashMap.setTabAt(var5_5, var8_8, ConcurrentHashMap.untreeify(var13_13.first));
                                    var15_15 = var6_6;
                                    ** GOTO lbl87
                                }
                            }
                            break block24;
                        }
                        if (var9_9 instanceof ReservationNode) {
                            var1_1 = new IllegalStateException("Recursive update");
                            throw var1_1;
                        }
                        var15_15 = var12_12 /* !! */ ;
                        var7_7 = var14_14;
                        break block24;
                    }
                    var7_7 = 1;
                    var15_15 = var9_9;
                    var11_11 = null;
                    do {
                        if (var15_15.hash == var4_4 && ((var12_12 /* !! */  = var15_15.key) == var6_6 || var12_12 /* !! */  != null && var6_6.equals(var12_12 /* !! */ ))) {
                            var12_12 /* !! */  = var15_15.val;
                            if (var3_3 != null && var3_3 != var12_12 /* !! */ ) {
                                var6_6 = var13_13;
                                if (var12_12 /* !! */  == null) break block23;
                                var6_6 = var13_13;
                                if (!var3_3.equals(var12_12 /* !! */ )) break block23;
                            }
                            var6_6 = var12_12 /* !! */ ;
                            if (var2_2 != null) {
                                var15_15.val = var2_2;
                            } else if (var11_11 != null) {
                                var11_11.next = var15_15.next;
                            } else {
                                ConcurrentHashMap.setTabAt(var5_5, var8_8, var15_15.next);
                            }
                            break block23;
                        }
                        var11_11 = var15_15;
                        var12_12 /* !! */  = var15_15.next;
                        var15_15 = var12_12 /* !! */ ;
                    } while (var12_12 /* !! */  != null);
                    var6_6 = var13_13;
                }
                var15_15 = var6_6;
            }
            // MONITOREXIT : var9_9
            if (var7_7 != 0) break;
        } while (true);
        if (var15_15 == null) return null;
        if (var2_2 != null) return (V)var15_15;
        this.addCount(-1L, -1);
        return (V)var15_15;
    }

    public <U> U search(long l, BiFunction<? super K, ? super V, ? extends U> biFunction) {
        if (biFunction != null) {
            return (U)new SearchMappingsTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, biFunction, new AtomicReference()).invoke();
        }
        throw new NullPointerException();
    }

    public <U> U searchEntries(long l, Function<Map.Entry<K, V>, ? extends U> function) {
        if (function != null) {
            return (U)new SearchEntriesTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, function, new AtomicReference()).invoke();
        }
        throw new NullPointerException();
    }

    public <U> U searchKeys(long l, Function<? super K, ? extends U> function) {
        if (function != null) {
            return (U)new SearchKeysTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, function, new AtomicReference()).invoke();
        }
        throw new NullPointerException();
    }

    public <U> U searchValues(long l, Function<? super V, ? extends U> function) {
        if (function != null) {
            return (U)new SearchValuesTask<K, V, U>(null, this.batchFor(l), 0, 0, this.table, function, new AtomicReference()).invoke();
        }
        throw new NullPointerException();
    }

    @Override
    public int size() {
        long l = this.sumCount();
        int n = l < 0L ? 0 : (l > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l);
        return n;
    }

    final long sumCount() {
        long l;
        CounterCell[] arrcounterCell = this.counterCells;
        long l2 = l = this.baseCount;
        if (arrcounterCell != null) {
            int n = 0;
            do {
                l2 = l;
                if (n >= arrcounterCell.length) break;
                CounterCell counterCell = arrcounterCell[n];
                l2 = l;
                if (counterCell != null) {
                    l2 = l + counterCell.value;
                }
                ++n;
                l = l2;
            } while (true);
        }
        return l2;
    }

    @Override
    public String toString() {
        Object object = this.table;
        int n = object == null ? 0 : ((Node<K, V>[])object).length;
        Traverser<K, V> traverser = new Traverser<K, V>((Node<K, V>[])object, n, 0, n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        Node<K, V> node = traverser.advance();
        object = node;
        if (node != null) {
            do {
                Object k = object.key;
                Object v = object.val;
                node = "(this Map)";
                object = k == this ? "(this Map)" : k;
                stringBuilder.append(object);
                stringBuilder.append('=');
                object = v == this ? node : v;
                stringBuilder.append(object);
                node = traverser.advance();
                object = node;
                if (node == null) break;
                stringBuilder.append(',');
                stringBuilder.append(' ');
            } while (true);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public Collection<V> values() {
        ValuesView<K, V> valuesView = this.values;
        if (valuesView == null) {
            this.values = valuesView = new ValuesView(this);
        }
        return valuesView;
    }

    static class BaseIterator<K, V>
    extends Traverser<K, V> {
        Node<K, V> lastReturned;
        final ConcurrentHashMap<K, V> map;

        BaseIterator(Node<K, V>[] arrnode, int n, int n2, int n3, ConcurrentHashMap<K, V> concurrentHashMap) {
            super(arrnode, n, n2, n3);
            this.map = concurrentHashMap;
            this.advance();
        }

        public final boolean hasMoreElements() {
            boolean bl = this.next != null;
            return bl;
        }

        public final boolean hasNext() {
            boolean bl = this.next != null;
            return bl;
        }

        public final void remove() {
            Node<K, V> node = this.lastReturned;
            if (node != null) {
                this.lastReturned = null;
                this.map.replaceNode(node.key, null, null);
                return;
            }
            throw new IllegalStateException();
        }
    }

    static abstract class BulkTask<K, V, R>
    extends CountedCompleter<R> {
        int baseIndex;
        int baseLimit;
        final int baseSize;
        int batch;
        int index;
        Node<K, V> next;
        TableStack<K, V> spare;
        TableStack<K, V> stack;
        Node<K, V>[] tab;

        BulkTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode) {
            super(bulkTask);
            this.batch = n;
            this.baseIndex = n2;
            this.index = n2;
            this.tab = arrnode;
            if (arrnode == null) {
                this.baseLimit = 0;
                this.baseSize = 0;
            } else if (bulkTask == null) {
                this.baseLimit = n = arrnode.length;
                this.baseSize = n;
            } else {
                this.baseLimit = n3;
                this.baseSize = bulkTask.baseSize;
            }
        }

        private void pushState(Node<K, V>[] arrnode, int n, int n2) {
            TableStack<K, V> tableStack = this.spare;
            if (tableStack != null) {
                this.spare = tableStack.next;
            } else {
                tableStack = new TableStack();
            }
            tableStack.tab = arrnode;
            tableStack.length = n2;
            tableStack.index = n;
            tableStack.next = this.stack;
            this.stack = tableStack;
        }

        private void recoverState(int n) {
            int n2;
            TableStack<K, V> tableStack;
            while ((tableStack = this.stack) != null) {
                int n3 = this.index;
                n2 = tableStack.length;
                this.index = n3 += n2;
                if (n3 < n) break;
                n = n2;
                this.index = tableStack.index;
                this.tab = tableStack.tab;
                tableStack.tab = null;
                TableStack tableStack2 = tableStack.next;
                tableStack.next = this.spare;
                this.stack = tableStack2;
                this.spare = tableStack;
            }
            if (tableStack == null) {
                this.index = n2 = this.index + this.baseSize;
                if (n2 >= n) {
                    this.baseIndex = n = this.baseIndex + 1;
                    this.index = n;
                }
            }
        }

        final Node<K, V> advance() {
            Node<K, V> node;
            Node<K, V> node2;
            Node<K, V> node3 = node2 = (node = this.next);
            if (node != null) {
                node3 = node2.next;
            }
            do {
                int n;
                int n2;
                Node<K, V>[] arrnode;
                if (node3 != null) {
                    this.next = node3;
                    return node3;
                }
                if (this.baseIndex >= this.baseLimit || (arrnode = this.tab) == null || (n = arrnode.length) <= (n2 = this.index) || n2 < 0) break;
                node3 = node2 = (node = ConcurrentHashMap.tabAt(arrnode, n2));
                if (node != null) {
                    node3 = node2;
                    if (node2.hash < 0) {
                        if (node2 instanceof ForwardingNode) {
                            this.tab = ((ForwardingNode)node2).nextTable;
                            node3 = null;
                            this.pushState(arrnode, n2, n);
                            continue;
                        }
                        node3 = node2 instanceof TreeBin ? ((TreeBin)node2).first : null;
                    }
                }
                if (this.stack != null) {
                    this.recoverState(n);
                    continue;
                }
                this.index = n2 = this.baseSize + n2;
                if (n2 < n) continue;
                this.baseIndex = n = this.baseIndex + 1;
                this.index = n;
            } while (true);
            this.next = null;
            return null;
        }
    }

    static abstract class CollectionView<K, V, E>
    implements Collection<E>,
    Serializable {
        private static final String OOME_MSG = "Required array size too large";
        private static final long serialVersionUID = 7249069246763182397L;
        final ConcurrentHashMap<K, V> map;

        CollectionView(ConcurrentHashMap<K, V> concurrentHashMap) {
            this.map = concurrentHashMap;
        }

        @Override
        public final void clear() {
            this.map.clear();
        }

        @Override
        public abstract boolean contains(Object var1);

        @Override
        public final boolean containsAll(Collection<?> object) {
            if (object != this) {
                object = object.iterator();
                while (object.hasNext()) {
                    E e = object.next();
                    if (e != null && this.contains(e)) continue;
                    return false;
                }
            }
            return true;
        }

        public ConcurrentHashMap<K, V> getMap() {
            return this.map;
        }

        @Override
        public final boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public abstract Iterator<E> iterator();

        @Override
        public abstract boolean remove(Object var1);

        @Override
        public final boolean removeAll(Collection<?> collection) {
            if (collection != null) {
                boolean bl = false;
                Iterator<E> iterator = this.iterator();
                while (iterator.hasNext()) {
                    if (!collection.contains(iterator.next())) continue;
                    iterator.remove();
                    bl = true;
                }
                return bl;
            }
            throw new NullPointerException();
        }

        @Override
        public final boolean retainAll(Collection<?> collection) {
            if (collection != null) {
                boolean bl = false;
                Iterator<E> iterator = this.iterator();
                while (iterator.hasNext()) {
                    if (collection.contains(iterator.next())) continue;
                    iterator.remove();
                    bl = true;
                }
                return bl;
            }
            throw new NullPointerException();
        }

        @Override
        public final int size() {
            return this.map.size();
        }

        @Override
        public final Object[] toArray() {
            long l = this.map.mappingCount();
            if (l <= 0x7FFFFFF7L) {
                int n = (int)l;
                Object[] arrobject = new Object[n];
                int n2 = 0;
                for (E e : this) {
                    int n3 = n;
                    Object[] arrobject2 = arrobject;
                    if (n2 == n) {
                        if (n < 2147483639) {
                            n = n >= 1073741819 ? 2147483639 : (n += (n >>> 1) + 1);
                            arrobject2 = Arrays.copyOf(arrobject, n);
                            n3 = n;
                        } else {
                            throw new OutOfMemoryError("Required array size too large");
                        }
                    }
                    arrobject2[n2] = e;
                    ++n2;
                    n = n3;
                    arrobject = arrobject2;
                }
                if (n2 != n) {
                    arrobject = Arrays.copyOf(arrobject, n2);
                }
                return arrobject;
            }
            throw new OutOfMemoryError("Required array size too large");
        }

        @Override
        public final <T> T[] toArray(T[] arrT) {
            long l = this.map.mappingCount();
            if (l <= 0x7FFFFFF7L) {
                int n = (int)l;
                Object[] arrobject = arrT.length >= n ? arrT : (Object[])Array.newInstance(arrT.getClass().getComponentType(), n);
                int n2 = arrobject.length;
                int n3 = 0;
                for (E e : this) {
                    Object[] arrobject2 = arrobject;
                    n = n2;
                    if (n3 == n2) {
                        if (n2 < 2147483639) {
                            n = n2 >= 1073741819 ? 2147483639 : n2 + ((n2 >>> 1) + 1);
                            arrobject2 = Arrays.copyOf(arrobject, n);
                        } else {
                            throw new OutOfMemoryError("Required array size too large");
                        }
                    }
                    arrobject2[n3] = e;
                    ++n3;
                    arrobject = arrobject2;
                    n2 = n;
                }
                if (arrT == arrobject && n3 < n2) {
                    arrobject[n3] = null;
                    return arrobject;
                }
                if (n3 != n2) {
                    arrobject = Arrays.copyOf(arrobject, n3);
                }
                return arrobject;
            }
            throw new OutOfMemoryError("Required array size too large");
        }

        public final String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            Iterator<E> iterator = this.iterator();
            if (iterator.hasNext()) {
                do {
                    Object object;
                    if ((object = iterator.next()) == this) {
                        object = "(this Collection)";
                    }
                    stringBuilder.append(object);
                    if (!iterator.hasNext()) break;
                    stringBuilder.append(',');
                    stringBuilder.append(' ');
                } while (true);
            }
            stringBuilder.append(']');
            return stringBuilder.toString();
        }
    }

    static final class CounterCell {
        volatile long value;

        CounterCell(long l) {
            this.value = l;
        }
    }

    static final class EntryIterator<K, V>
    extends BaseIterator<K, V>
    implements Iterator<Map.Entry<K, V>> {
        EntryIterator(Node<K, V>[] arrnode, int n, int n2, int n3, ConcurrentHashMap<K, V> concurrentHashMap) {
            super(arrnode, n, n2, n3, concurrentHashMap);
        }

        @Override
        public final Map.Entry<K, V> next() {
            Node node = this.next;
            if (node != null) {
                Object k = node.key;
                Object v = node.val;
                this.lastReturned = node;
                this.advance();
                return new MapEntry(k, v, this.map);
            }
            throw new NoSuchElementException();
        }
    }

    static final class EntrySetView<K, V>
    extends CollectionView<K, V, Map.Entry<K, V>>
    implements Set<Map.Entry<K, V>>,
    Serializable {
        private static final long serialVersionUID = 2249069246763182397L;

        EntrySetView(ConcurrentHashMap<K, V> concurrentHashMap) {
            super(concurrentHashMap);
        }

        @Override
        public boolean add(Map.Entry<K, V> entry) {
            ConcurrentHashMap concurrentHashMap = this.map;
            K k = entry.getKey();
            entry = entry.getValue();
            boolean bl = false;
            if (concurrentHashMap.putVal(k, entry, false) == null) {
                bl = true;
            }
            return bl;
        }

        @Override
        public boolean addAll(Collection<? extends Map.Entry<K, V>> object) {
            boolean bl = false;
            object = object.iterator();
            while (object.hasNext()) {
                if (!this.add((Map.Entry)object.next())) continue;
                bl = true;
            }
            return bl;
        }

        @Override
        public boolean contains(Object object) {
            Object object2;
            boolean bl = object instanceof Map.Entry && (object2 = (object = (Map.Entry)object).getKey()) != null && (object2 = this.map.get(object2)) != null && (object = object.getValue()) != null && (object == object2 || object.equals(object2));
            return bl;
        }

        @Override
        public final boolean equals(Object object) {
            boolean bl = object instanceof Set && ((object = (Set)object) == this || this.containsAll((Collection<?>)object) && object.containsAll(this));
            return bl;
        }

        @Override
        public void forEach(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                Object object = this.map.table;
                if (object != null) {
                    Node node;
                    object = new Traverser((Node<K, V>[])object, ((Node<K, V>[])object).length, 0, ((Node<K, V>[])object).length);
                    while ((node = ((Traverser)object).advance()) != null) {
                        consumer.accept(new MapEntry(node.key, node.val, this.map));
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final int hashCode() {
            int n = 0;
            int n2 = 0;
            Object object = this.map.table;
            if (object != null) {
                object = new Traverser((Node<K, V>[])object, ((Node<K, V>[])object).length, 0, ((Node<K, V>[])object).length);
                do {
                    Node node = ((Traverser)object).advance();
                    n = n2;
                    if (node == null) break;
                    n2 += node.hashCode();
                } while (true);
            }
            return n;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            Node<K, V>[] arrnode = concurrentHashMap.table;
            int n = arrnode == null ? 0 : arrnode.length;
            return new EntryIterator(arrnode, n, 0, n, concurrentHashMap);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) return false;
            Map.Entry entry = (Map.Entry)object;
            object = entry.getKey();
            if (object == null) return false;
            Object v = entry.getValue();
            if (v == null) return false;
            if (!this.map.remove(object, v)) return false;
            return true;
        }

        @Override
        public boolean removeIf(Predicate<? super Map.Entry<K, V>> predicate) {
            return this.map.removeEntryIf(predicate);
        }

        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            long l = concurrentHashMap.sumCount();
            Node<K, V>[] arrnode = concurrentHashMap.table;
            int n = arrnode == null ? 0 : arrnode.length;
            if (l < 0L) {
                l = 0L;
            }
            return new EntrySpliterator(arrnode, n, 0, n, l, concurrentHashMap);
        }
    }

    static final class EntrySpliterator<K, V>
    extends Traverser<K, V>
    implements Spliterator<Map.Entry<K, V>> {
        long est;
        final ConcurrentHashMap<K, V> map;

        EntrySpliterator(Node<K, V>[] arrnode, int n, int n2, int n3, long l, ConcurrentHashMap<K, V> concurrentHashMap) {
            super(arrnode, n, n2, n3);
            this.map = concurrentHashMap;
            this.est = l;
        }

        @Override
        public int characteristics() {
            return 4353;
        }

        @Override
        public long estimateSize() {
            return this.est;
        }

        @Override
        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                Node node;
                while ((node = this.advance()) != null) {
                    consumer.accept(new MapEntry(node.key, node.val, this.map));
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> consumer) {
            if (consumer != null) {
                Node node = this.advance();
                if (node == null) {
                    return false;
                }
                consumer.accept(new MapEntry(node.key, node.val, this.map));
                return true;
            }
            throw new NullPointerException();
        }

        public EntrySpliterator<K, V> trySplit() {
            Object object;
            int n = this.baseIndex;
            int n2 = this.baseLimit;
            int n3 = n + n2 >>> 1;
            if (n3 <= n) {
                object = null;
            } else {
                long l;
                object = this.tab;
                n = this.baseSize;
                this.baseLimit = n3;
                this.est = l = this.est >>> 1;
                object = new EntrySpliterator<K, V>((Node<K, V>[])object, n, n3, n2, l, this.map);
            }
            return object;
        }
    }

    static final class ForEachEntryTask<K, V>
    extends BulkTask<K, V, Void> {
        final Consumer<? super Map.Entry<K, V>> action;

        ForEachEntryTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, Consumer<? super Map.Entry<K, V>> consumer) {
            super(bulkTask, n, n2, n3, arrnode);
            this.action = consumer;
        }

        @Override
        public final void compute() {
            Consumer<Map.Entry<K, V>> consumer = this.action;
            if (consumer != null) {
                int n;
                Node node;
                int n2;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    new ForEachEntryTask<K, V>(this, n4, n, n2, this.tab, consumer).fork();
                }
                while ((node = this.advance()) != null) {
                    consumer.accept(node);
                }
                this.propagateCompletion();
            }
        }
    }

    static final class ForEachKeyTask<K, V>
    extends BulkTask<K, V, Void> {
        final Consumer<? super K> action;

        ForEachKeyTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, Consumer<? super K> consumer) {
            super(bulkTask, n, n2, n3, arrnode);
            this.action = consumer;
        }

        @Override
        public final void compute() {
            Consumer<K> consumer = this.action;
            if (consumer != null) {
                int n;
                Node node;
                int n2;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    new ForEachKeyTask<K, V>(this, n4, n, n2, this.tab, consumer).fork();
                }
                while ((node = this.advance()) != null) {
                    consumer.accept(node.key);
                }
                this.propagateCompletion();
            }
        }
    }

    static final class ForEachMappingTask<K, V>
    extends BulkTask<K, V, Void> {
        final BiConsumer<? super K, ? super V> action;

        ForEachMappingTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, BiConsumer<? super K, ? super V> biConsumer) {
            super(bulkTask, n, n2, n3, arrnode);
            this.action = biConsumer;
        }

        @Override
        public final void compute() {
            BiConsumer<K, V> biConsumer = this.action;
            if (biConsumer != null) {
                int n;
                Node node;
                int n2;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    new ForEachMappingTask<K, V>(this, n4, n, n2, this.tab, biConsumer).fork();
                }
                while ((node = this.advance()) != null) {
                    biConsumer.accept(node.key, node.val);
                }
                this.propagateCompletion();
            }
        }
    }

    static final class ForEachTransformedEntryTask<K, V, U>
    extends BulkTask<K, V, Void> {
        final Consumer<? super U> action;
        final Function<Map.Entry<K, V>, ? extends U> transformer;

        ForEachTransformedEntryTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, Function<Map.Entry<K, V>, ? extends U> function, Consumer<? super U> consumer) {
            super(bulkTask, n, n2, n3, arrnode);
            this.transformer = function;
            this.action = consumer;
        }

        @Override
        public final void compute() {
            Consumer<U> consumer;
            Function<Map.Entry<K, V>, U> function = this.transformer;
            if (function != null && (consumer = this.action) != null) {
                int n;
                int n2;
                Node node;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    new ForEachTransformedEntryTask<K, V, U>(this, n4, n2, n, this.tab, function, consumer).fork();
                }
                while ((node = this.advance()) != null) {
                    if ((node = function.apply(node)) == null) continue;
                    consumer.accept(node);
                }
                this.propagateCompletion();
            }
        }
    }

    static final class ForEachTransformedKeyTask<K, V, U>
    extends BulkTask<K, V, Void> {
        final Consumer<? super U> action;
        final Function<? super K, ? extends U> transformer;

        ForEachTransformedKeyTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, Function<? super K, ? extends U> function, Consumer<? super U> consumer) {
            super(bulkTask, n, n2, n3, arrnode);
            this.transformer = function;
            this.action = consumer;
        }

        @Override
        public final void compute() {
            Consumer<U> consumer;
            Function<K, U> function = this.transformer;
            if (function != null && (consumer = this.action) != null) {
                int n;
                int n2;
                Node node;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    new ForEachTransformedKeyTask<K, V, U>(this, n4, n2, n, this.tab, function, consumer).fork();
                }
                while ((node = this.advance()) != null) {
                    node = function.apply(node.key);
                    if (node == null) continue;
                    consumer.accept(node);
                }
                this.propagateCompletion();
            }
        }
    }

    static final class ForEachTransformedMappingTask<K, V, U>
    extends BulkTask<K, V, Void> {
        final Consumer<? super U> action;
        final BiFunction<? super K, ? super V, ? extends U> transformer;

        ForEachTransformedMappingTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, BiFunction<? super K, ? super V, ? extends U> biFunction, Consumer<? super U> consumer) {
            super(bulkTask, n, n2, n3, arrnode);
            this.transformer = biFunction;
            this.action = consumer;
        }

        @Override
        public final void compute() {
            Consumer<U> consumer;
            BiFunction biFunction = this.transformer;
            if (biFunction != null && (consumer = this.action) != null) {
                int n;
                int n2;
                Node node;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    new ForEachTransformedMappingTask<K, V, U>(this, n4, n2, n, this.tab, biFunction, consumer).fork();
                }
                while ((node = this.advance()) != null) {
                    node = biFunction.apply(node.key, node.val);
                    if (node == null) continue;
                    consumer.accept(node);
                }
                this.propagateCompletion();
            }
        }
    }

    static final class ForEachTransformedValueTask<K, V, U>
    extends BulkTask<K, V, Void> {
        final Consumer<? super U> action;
        final Function<? super V, ? extends U> transformer;

        ForEachTransformedValueTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, Function<? super V, ? extends U> function, Consumer<? super U> consumer) {
            super(bulkTask, n, n2, n3, arrnode);
            this.transformer = function;
            this.action = consumer;
        }

        @Override
        public final void compute() {
            Consumer<U> consumer;
            Function<V, U> function = this.transformer;
            if (function != null && (consumer = this.action) != null) {
                int n;
                int n2;
                Node node;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    new ForEachTransformedValueTask<K, V, U>(this, n4, n2, n, this.tab, function, consumer).fork();
                }
                while ((node = this.advance()) != null) {
                    node = function.apply(node.val);
                    if (node == null) continue;
                    consumer.accept(node);
                }
                this.propagateCompletion();
            }
        }
    }

    static final class ForEachValueTask<K, V>
    extends BulkTask<K, V, Void> {
        final Consumer<? super V> action;

        ForEachValueTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, Consumer<? super V> consumer) {
            super(bulkTask, n, n2, n3, arrnode);
            this.action = consumer;
        }

        @Override
        public final void compute() {
            Consumer<V> consumer = this.action;
            if (consumer != null) {
                int n;
                Node node;
                int n2;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    new ForEachValueTask<K, V>(this, n4, n, n2, this.tab, consumer).fork();
                }
                while ((node = this.advance()) != null) {
                    consumer.accept(node.val);
                }
                this.propagateCompletion();
            }
        }
    }

    static final class ForwardingNode<K, V>
    extends Node<K, V> {
        final Node<K, V>[] nextTable;

        ForwardingNode(Node<K, V>[] arrnode) {
            super(-1, null, null, null);
            this.nextTable = arrnode;
        }

        @Override
        Node<K, V> find(int n, Object object) {
            int n2;
            Object object2 = this.nextTable;
            block0 : while (object != null && object2 != null && (n2 = ((Node<K, V>[])object2).length) != 0) {
                Node<K, V> node = ConcurrentHashMap.tabAt(object2, n2 - 1 & n);
                object2 = node;
                if (node == null) break;
                do {
                    if ((n2 = object2.hash) == n && ((node = object2.key) == object || node != null && object.equals(node))) {
                        return object2;
                    }
                    if (n2 < 0) {
                        if (object2 instanceof ForwardingNode) {
                            object2 = ((ForwardingNode)object2).nextTable;
                            continue block0;
                        }
                        return object2.find(n, object);
                    }
                    node = object2.next;
                    object2 = node;
                } while (node != null);
                return null;
            }
            return null;
        }
    }

    static final class KeyIterator<K, V>
    extends BaseIterator<K, V>
    implements Iterator<K>,
    Enumeration<K> {
        KeyIterator(Node<K, V>[] arrnode, int n, int n2, int n3, ConcurrentHashMap<K, V> concurrentHashMap) {
            super(arrnode, n, n2, n3, concurrentHashMap);
        }

        @Override
        public final K next() {
            Node node = this.next;
            if (node != null) {
                Object k = node.key;
                this.lastReturned = node;
                this.advance();
                return k;
            }
            throw new NoSuchElementException();
        }

        @Override
        public final K nextElement() {
            return this.next();
        }
    }

    public static class KeySetView<K, V>
    extends CollectionView<K, V, K>
    implements Set<K>,
    Serializable {
        private static final long serialVersionUID = 7249069246763182397L;
        private final V value;

        KeySetView(ConcurrentHashMap<K, V> concurrentHashMap, V v) {
            super(concurrentHashMap);
            this.value = v;
        }

        @Override
        public boolean add(K k) {
            V v = this.value;
            if (v != null) {
                ConcurrentHashMap concurrentHashMap = this.map;
                boolean bl = true;
                if (concurrentHashMap.putVal(k, v, true) != null) {
                    bl = false;
                }
                return bl;
            }
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends K> object) {
            boolean bl = false;
            V v = this.value;
            if (v != null) {
                object = object.iterator();
                while (object.hasNext()) {
                    Object e = object.next();
                    if (this.map.putVal(e, v, true) != null) continue;
                    bl = true;
                }
                return bl;
            }
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object object) {
            return this.map.containsKey(object);
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = object instanceof Set && ((object = (Set)object) == this || this.containsAll((Collection<?>)object) && object.containsAll(this));
            return bl;
        }

        @Override
        public void forEach(Consumer<? super K> consumer) {
            if (consumer != null) {
                Object object = this.map.table;
                if (object != null) {
                    Traverser traverser = new Traverser((Node<K, V>[])object, ((Node<K, V>[])object).length, 0, ((Node<K, V>[])object).length);
                    while ((object = traverser.advance()) != null) {
                        consumer.accept(object.key);
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        public V getMappedValue() {
            return this.value;
        }

        @Override
        public int hashCode() {
            int n = 0;
            Iterator<K> iterator = this.iterator();
            while (iterator.hasNext()) {
                n += iterator.next().hashCode();
            }
            return n;
        }

        @Override
        public Iterator<K> iterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            Node<K, V>[] arrnode = concurrentHashMap.table;
            int n = arrnode == null ? 0 : arrnode.length;
            return new KeyIterator(arrnode, n, 0, n, concurrentHashMap);
        }

        @Override
        public boolean remove(Object object) {
            boolean bl = this.map.remove(object) != null;
            return bl;
        }

        @Override
        public Spliterator<K> spliterator() {
            Node<K, V>[] arrnode = this.map;
            long l = arrnode.sumCount();
            arrnode = arrnode.table;
            int n = arrnode == null ? 0 : arrnode.length;
            if (l < 0L) {
                l = 0L;
            }
            return new KeySpliterator(arrnode, n, 0, n, l);
        }
    }

    static final class KeySpliterator<K, V>
    extends Traverser<K, V>
    implements Spliterator<K> {
        long est;

        KeySpliterator(Node<K, V>[] arrnode, int n, int n2, int n3, long l) {
            super(arrnode, n, n2, n3);
            this.est = l;
        }

        @Override
        public int characteristics() {
            return 4353;
        }

        @Override
        public long estimateSize() {
            return this.est;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            if (consumer != null) {
                Node node;
                while ((node = this.advance()) != null) {
                    consumer.accept(node.key);
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (consumer != null) {
                Node node = this.advance();
                if (node == null) {
                    return false;
                }
                consumer.accept(node.key);
                return true;
            }
            throw new NullPointerException();
        }

        public KeySpliterator<K, V> trySplit() {
            Object object;
            int n = this.baseIndex;
            int n2 = this.baseLimit;
            int n3 = n + n2 >>> 1;
            if (n3 <= n) {
                object = null;
            } else {
                long l;
                object = this.tab;
                n = this.baseSize;
                this.baseLimit = n3;
                this.est = l = this.est >>> 1;
                object = new KeySpliterator<K, V>((Node<K, V>[])object, n, n3, n2, l);
            }
            return object;
        }
    }

    static final class MapEntry<K, V>
    implements Map.Entry<K, V> {
        final K key;
        final ConcurrentHashMap<K, V> map;
        V val;

        MapEntry(K k, V v, ConcurrentHashMap<K, V> concurrentHashMap) {
            this.key = k;
            this.val = v;
            this.map = concurrentHashMap;
        }

        @Override
        public boolean equals(Object object) {
            Object object2;
            K k;
            boolean bl = !(!(object instanceof Map.Entry) || (object2 = (object = (Map.Entry)object).getKey()) == null || (object = object.getValue()) == null || object2 != (k = this.key) && !object2.equals(k) || object != (object2 = this.val) && !object.equals(object2));
            return bl;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.val;
        }

        @Override
        public int hashCode() {
            return this.key.hashCode() ^ this.val.hashCode();
        }

        @Override
        public V setValue(V v) {
            if (v != null) {
                V v2 = this.val;
                this.val = v;
                this.map.put(this.key, v);
                return v2;
            }
            throw new NullPointerException();
        }

        public String toString() {
            return Helpers.mapEntryToString(this.key, this.val);
        }
    }

    static final class MapReduceEntriesTask<K, V, U>
    extends BulkTask<K, V, U> {
        MapReduceEntriesTask<K, V, U> nextRight;
        final BiFunction<? super U, ? super U, ? extends U> reducer;
        U result;
        MapReduceEntriesTask<K, V, U> rights;
        final Function<Map.Entry<K, V>, ? extends U> transformer;

        MapReduceEntriesTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceEntriesTask<K, V, U> mapReduceEntriesTask, Function<Map.Entry<K, V>, ? extends U> function, BiFunction<? super U, ? super U, ? extends U> biFunction) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceEntriesTask;
            this.transformer = function;
            this.reducer = biFunction;
        }

        @Override
        public final void compute() {
            BiFunction<U, U, U> biFunction;
            Function<Map.Entry<K, V>, U> function = this.transformer;
            if (function != null && (biFunction = this.reducer) != null) {
                U u;
                int n;
                int n2;
                CountedCompleter<Object> countedCompleter;
                MapReduceEntriesTask<K, V, U> mapReduceEntriesTask;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    countedCompleter = new MapReduceEntriesTask(this, n4, n2, n, this.tab, this.rights, function, biFunction);
                    this.rights = countedCompleter;
                    countedCompleter.fork();
                }
                countedCompleter = null;
                while ((mapReduceEntriesTask = this.advance()) != null) {
                    u = function.apply((Map.Entry<K, V>)((Object)mapReduceEntriesTask));
                    mapReduceEntriesTask = countedCompleter;
                    if (u != null) {
                        countedCompleter = countedCompleter == null ? u : biFunction.apply(countedCompleter, u);
                        mapReduceEntriesTask = countedCompleter;
                    }
                    countedCompleter = mapReduceEntriesTask;
                }
                this.result = countedCompleter;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    function = (MapReduceEntriesTask)countedCompleter;
                    mapReduceEntriesTask = ((MapReduceEntriesTask)function).rights;
                    while (mapReduceEntriesTask != null) {
                        u = mapReduceEntriesTask.result;
                        if (u != null) {
                            U u2 = ((MapReduceEntriesTask)function).result;
                            if (u2 != null) {
                                u = biFunction.apply(u2, u);
                            }
                            ((MapReduceEntriesTask)function).result = u;
                        }
                        mapReduceEntriesTask = mapReduceEntriesTask.nextRight;
                        ((MapReduceEntriesTask)function).rights = mapReduceEntriesTask;
                    }
                }
            }
        }

        @Override
        public final U getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceEntriesToDoubleTask<K, V>
    extends BulkTask<K, V, Double> {
        final double basis;
        MapReduceEntriesToDoubleTask<K, V> nextRight;
        final DoubleBinaryOperator reducer;
        double result;
        MapReduceEntriesToDoubleTask<K, V> rights;
        final ToDoubleFunction<Map.Entry<K, V>> transformer;

        MapReduceEntriesToDoubleTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceEntriesToDoubleTask<K, V> mapReduceEntriesToDoubleTask, ToDoubleFunction<Map.Entry<K, V>> toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceEntriesToDoubleTask;
            this.transformer = toDoubleFunction;
            this.basis = d;
            this.reducer = doubleBinaryOperator;
        }

        @Override
        public final void compute() {
            DoubleBinaryOperator doubleBinaryOperator;
            MapReduceEntriesToDoubleTask<K, V> mapReduceEntriesToDoubleTask = this.transformer;
            CountedCompleter<?> countedCompleter = mapReduceEntriesToDoubleTask;
            if (mapReduceEntriesToDoubleTask != null && (doubleBinaryOperator = this.reducer) != null) {
                int n;
                int n2;
                double d = this.basis;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    mapReduceEntriesToDoubleTask = new MapReduceEntriesToDoubleTask<K, V>(this, n4, n, n2, this.tab, this.rights, (ToDoubleFunction<Map.Entry<K, V>>)((Object)countedCompleter), d, doubleBinaryOperator);
                    this.rights = mapReduceEntriesToDoubleTask;
                    mapReduceEntriesToDoubleTask.fork();
                }
                while ((mapReduceEntriesToDoubleTask = this.advance()) != null) {
                    d = doubleBinaryOperator.applyAsDouble(d, countedCompleter.applyAsDouble(mapReduceEntriesToDoubleTask));
                }
                this.result = d;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    MapReduceEntriesToDoubleTask mapReduceEntriesToDoubleTask2 = (MapReduceEntriesToDoubleTask)countedCompleter;
                    mapReduceEntriesToDoubleTask = mapReduceEntriesToDoubleTask2.rights;
                    while (mapReduceEntriesToDoubleTask != null) {
                        mapReduceEntriesToDoubleTask2.result = doubleBinaryOperator.applyAsDouble(mapReduceEntriesToDoubleTask2.result, mapReduceEntriesToDoubleTask.result);
                        mapReduceEntriesToDoubleTask = mapReduceEntriesToDoubleTask.nextRight;
                        mapReduceEntriesToDoubleTask2.rights = mapReduceEntriesToDoubleTask;
                    }
                }
            }
        }

        @Override
        public final Double getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceEntriesToIntTask<K, V>
    extends BulkTask<K, V, Integer> {
        final int basis;
        MapReduceEntriesToIntTask<K, V> nextRight;
        final IntBinaryOperator reducer;
        int result;
        MapReduceEntriesToIntTask<K, V> rights;
        final ToIntFunction<Map.Entry<K, V>> transformer;

        MapReduceEntriesToIntTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceEntriesToIntTask<K, V> mapReduceEntriesToIntTask, ToIntFunction<Map.Entry<K, V>> toIntFunction, int n4, IntBinaryOperator intBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceEntriesToIntTask;
            this.transformer = toIntFunction;
            this.basis = n4;
            this.reducer = intBinaryOperator;
        }

        @Override
        public final void compute() {
            IntBinaryOperator intBinaryOperator;
            Object object = this.transformer;
            if (object != null && (intBinaryOperator = this.reducer) != null) {
                MapReduceEntriesToIntTask<K, V> mapReduceEntriesToIntTask;
                int n;
                int n2;
                int n3 = this.basis;
                int n4 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n4 >>> 1) > n4) {
                    int n5;
                    this.addToPendingCount(1);
                    this.batch = n5 = this.batch >>> 1;
                    this.baseLimit = n2;
                    mapReduceEntriesToIntTask = new MapReduceEntriesToIntTask<K, V>(this, n5, n2, n, this.tab, this.rights, (ToIntFunction<Map.Entry<K, V>>)object, n3, intBinaryOperator);
                    this.rights = mapReduceEntriesToIntTask;
                    mapReduceEntriesToIntTask.fork();
                }
                while ((mapReduceEntriesToIntTask = this.advance()) != null) {
                    n3 = intBinaryOperator.applyAsInt(n3, object.applyAsInt(mapReduceEntriesToIntTask));
                }
                this.result = n3;
                for (object = this.firstComplete(); object != null; object = object.nextComplete()) {
                    MapReduceEntriesToIntTask mapReduceEntriesToIntTask2 = (MapReduceEntriesToIntTask)object;
                    mapReduceEntriesToIntTask = mapReduceEntriesToIntTask2.rights;
                    while (mapReduceEntriesToIntTask != null) {
                        mapReduceEntriesToIntTask2.result = intBinaryOperator.applyAsInt(mapReduceEntriesToIntTask2.result, mapReduceEntriesToIntTask.result);
                        mapReduceEntriesToIntTask = mapReduceEntriesToIntTask.nextRight;
                        mapReduceEntriesToIntTask2.rights = mapReduceEntriesToIntTask;
                    }
                }
            }
        }

        @Override
        public final Integer getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceEntriesToLongTask<K, V>
    extends BulkTask<K, V, Long> {
        final long basis;
        MapReduceEntriesToLongTask<K, V> nextRight;
        final LongBinaryOperator reducer;
        long result;
        MapReduceEntriesToLongTask<K, V> rights;
        final ToLongFunction<Map.Entry<K, V>> transformer;

        MapReduceEntriesToLongTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceEntriesToLongTask<K, V> mapReduceEntriesToLongTask, ToLongFunction<Map.Entry<K, V>> toLongFunction, long l, LongBinaryOperator longBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceEntriesToLongTask;
            this.transformer = toLongFunction;
            this.basis = l;
            this.reducer = longBinaryOperator;
        }

        @Override
        public final void compute() {
            LongBinaryOperator longBinaryOperator;
            MapReduceEntriesToLongTask<K, V> mapReduceEntriesToLongTask = this.transformer;
            CountedCompleter<?> countedCompleter = mapReduceEntriesToLongTask;
            if (mapReduceEntriesToLongTask != null && (longBinaryOperator = this.reducer) != null) {
                int n;
                int n2;
                long l = this.basis;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    mapReduceEntriesToLongTask = new MapReduceEntriesToLongTask<K, V>(this, n4, n, n2, this.tab, this.rights, (ToLongFunction<Map.Entry<K, V>>)((Object)countedCompleter), l, longBinaryOperator);
                    this.rights = mapReduceEntriesToLongTask;
                    mapReduceEntriesToLongTask.fork();
                }
                while ((mapReduceEntriesToLongTask = this.advance()) != null) {
                    l = longBinaryOperator.applyAsLong(l, countedCompleter.applyAsLong(mapReduceEntriesToLongTask));
                }
                this.result = l;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    MapReduceEntriesToLongTask mapReduceEntriesToLongTask2 = (MapReduceEntriesToLongTask)countedCompleter;
                    mapReduceEntriesToLongTask = mapReduceEntriesToLongTask2.rights;
                    while (mapReduceEntriesToLongTask != null) {
                        mapReduceEntriesToLongTask2.result = longBinaryOperator.applyAsLong(mapReduceEntriesToLongTask2.result, mapReduceEntriesToLongTask.result);
                        mapReduceEntriesToLongTask = mapReduceEntriesToLongTask.nextRight;
                        mapReduceEntriesToLongTask2.rights = mapReduceEntriesToLongTask;
                    }
                }
            }
        }

        @Override
        public final Long getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceKeysTask<K, V, U>
    extends BulkTask<K, V, U> {
        MapReduceKeysTask<K, V, U> nextRight;
        final BiFunction<? super U, ? super U, ? extends U> reducer;
        U result;
        MapReduceKeysTask<K, V, U> rights;
        final Function<? super K, ? extends U> transformer;

        MapReduceKeysTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceKeysTask<K, V, U> mapReduceKeysTask, Function<? super K, ? extends U> function, BiFunction<? super U, ? super U, ? extends U> biFunction) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceKeysTask;
            this.transformer = function;
            this.reducer = biFunction;
        }

        @Override
        public final void compute() {
            BiFunction<U, U, U> biFunction;
            Function<K, U> function = this.transformer;
            if (function != null && (biFunction = this.reducer) != null) {
                MapReduceKeysTask<K, V, U> mapReduceKeysTask;
                U u;
                int n;
                int n2;
                CountedCompleter<Object> countedCompleter;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    countedCompleter = new MapReduceKeysTask(this, n4, n2, n, this.tab, this.rights, function, biFunction);
                    this.rights = countedCompleter;
                    countedCompleter.fork();
                }
                countedCompleter = null;
                while ((mapReduceKeysTask = this.advance()) != null) {
                    u = function.apply(((Node)mapReduceKeysTask).key);
                    mapReduceKeysTask = countedCompleter;
                    if (u != null) {
                        countedCompleter = countedCompleter == null ? u : biFunction.apply(countedCompleter, u);
                        mapReduceKeysTask = countedCompleter;
                    }
                    countedCompleter = mapReduceKeysTask;
                }
                this.result = countedCompleter;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    function = (MapReduceKeysTask)countedCompleter;
                    mapReduceKeysTask = ((MapReduceKeysTask)function).rights;
                    while (mapReduceKeysTask != null) {
                        u = mapReduceKeysTask.result;
                        if (u != null) {
                            U u2 = ((MapReduceKeysTask)function).result;
                            if (u2 != null) {
                                u = biFunction.apply(u2, u);
                            }
                            ((MapReduceKeysTask)function).result = u;
                        }
                        mapReduceKeysTask = mapReduceKeysTask.nextRight;
                        ((MapReduceKeysTask)function).rights = mapReduceKeysTask;
                    }
                }
            }
        }

        @Override
        public final U getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceKeysToDoubleTask<K, V>
    extends BulkTask<K, V, Double> {
        final double basis;
        MapReduceKeysToDoubleTask<K, V> nextRight;
        final DoubleBinaryOperator reducer;
        double result;
        MapReduceKeysToDoubleTask<K, V> rights;
        final ToDoubleFunction<? super K> transformer;

        MapReduceKeysToDoubleTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceKeysToDoubleTask<K, V> mapReduceKeysToDoubleTask, ToDoubleFunction<? super K> toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceKeysToDoubleTask;
            this.transformer = toDoubleFunction;
            this.basis = d;
            this.reducer = doubleBinaryOperator;
        }

        @Override
        public final void compute() {
            DoubleBinaryOperator doubleBinaryOperator;
            MapReduceKeysToDoubleTask<K, V> mapReduceKeysToDoubleTask = this.transformer;
            CountedCompleter<?> countedCompleter = mapReduceKeysToDoubleTask;
            if (mapReduceKeysToDoubleTask != null && (doubleBinaryOperator = this.reducer) != null) {
                int n;
                int n2;
                double d = this.basis;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    mapReduceKeysToDoubleTask = new MapReduceKeysToDoubleTask<K, V>(this, n4, n, n2, this.tab, this.rights, (ToDoubleFunction<K>)((Object)countedCompleter), d, doubleBinaryOperator);
                    this.rights = mapReduceKeysToDoubleTask;
                    mapReduceKeysToDoubleTask.fork();
                }
                while ((mapReduceKeysToDoubleTask = this.advance()) != null) {
                    d = doubleBinaryOperator.applyAsDouble(d, countedCompleter.applyAsDouble(((Node)mapReduceKeysToDoubleTask).key));
                }
                this.result = d;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    MapReduceKeysToDoubleTask mapReduceKeysToDoubleTask2 = (MapReduceKeysToDoubleTask)countedCompleter;
                    mapReduceKeysToDoubleTask = mapReduceKeysToDoubleTask2.rights;
                    while (mapReduceKeysToDoubleTask != null) {
                        mapReduceKeysToDoubleTask2.result = doubleBinaryOperator.applyAsDouble(mapReduceKeysToDoubleTask2.result, mapReduceKeysToDoubleTask.result);
                        mapReduceKeysToDoubleTask = mapReduceKeysToDoubleTask.nextRight;
                        mapReduceKeysToDoubleTask2.rights = mapReduceKeysToDoubleTask;
                    }
                }
            }
        }

        @Override
        public final Double getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceKeysToIntTask<K, V>
    extends BulkTask<K, V, Integer> {
        final int basis;
        MapReduceKeysToIntTask<K, V> nextRight;
        final IntBinaryOperator reducer;
        int result;
        MapReduceKeysToIntTask<K, V> rights;
        final ToIntFunction<? super K> transformer;

        MapReduceKeysToIntTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceKeysToIntTask<K, V> mapReduceKeysToIntTask, ToIntFunction<? super K> toIntFunction, int n4, IntBinaryOperator intBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceKeysToIntTask;
            this.transformer = toIntFunction;
            this.basis = n4;
            this.reducer = intBinaryOperator;
        }

        @Override
        public final void compute() {
            IntBinaryOperator intBinaryOperator;
            Object object = this.transformer;
            if (object != null && (intBinaryOperator = this.reducer) != null) {
                int n;
                int n2;
                MapReduceKeysToIntTask<K, V> mapReduceKeysToIntTask;
                int n3 = this.basis;
                int n4 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n4 >>> 1) > n4) {
                    int n5;
                    this.addToPendingCount(1);
                    this.batch = n5 = this.batch >>> 1;
                    this.baseLimit = n2;
                    mapReduceKeysToIntTask = new MapReduceKeysToIntTask<K, V>(this, n5, n2, n, this.tab, this.rights, (ToIntFunction<K>)object, n3, intBinaryOperator);
                    this.rights = mapReduceKeysToIntTask;
                    mapReduceKeysToIntTask.fork();
                }
                while ((mapReduceKeysToIntTask = this.advance()) != null) {
                    n3 = intBinaryOperator.applyAsInt(n3, object.applyAsInt(((Node)mapReduceKeysToIntTask).key));
                }
                this.result = n3;
                for (object = this.firstComplete(); object != null; object = object.nextComplete()) {
                    MapReduceKeysToIntTask mapReduceKeysToIntTask2 = (MapReduceKeysToIntTask)object;
                    mapReduceKeysToIntTask = mapReduceKeysToIntTask2.rights;
                    while (mapReduceKeysToIntTask != null) {
                        mapReduceKeysToIntTask2.result = intBinaryOperator.applyAsInt(mapReduceKeysToIntTask2.result, mapReduceKeysToIntTask.result);
                        mapReduceKeysToIntTask = mapReduceKeysToIntTask.nextRight;
                        mapReduceKeysToIntTask2.rights = mapReduceKeysToIntTask;
                    }
                }
            }
        }

        @Override
        public final Integer getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceKeysToLongTask<K, V>
    extends BulkTask<K, V, Long> {
        final long basis;
        MapReduceKeysToLongTask<K, V> nextRight;
        final LongBinaryOperator reducer;
        long result;
        MapReduceKeysToLongTask<K, V> rights;
        final ToLongFunction<? super K> transformer;

        MapReduceKeysToLongTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceKeysToLongTask<K, V> mapReduceKeysToLongTask, ToLongFunction<? super K> toLongFunction, long l, LongBinaryOperator longBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceKeysToLongTask;
            this.transformer = toLongFunction;
            this.basis = l;
            this.reducer = longBinaryOperator;
        }

        @Override
        public final void compute() {
            LongBinaryOperator longBinaryOperator;
            MapReduceKeysToLongTask<K, V> mapReduceKeysToLongTask = this.transformer;
            CountedCompleter<?> countedCompleter = mapReduceKeysToLongTask;
            if (mapReduceKeysToLongTask != null && (longBinaryOperator = this.reducer) != null) {
                int n;
                int n2;
                long l = this.basis;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    mapReduceKeysToLongTask = new MapReduceKeysToLongTask<K, V>(this, n4, n, n2, this.tab, this.rights, (ToLongFunction<K>)((Object)countedCompleter), l, longBinaryOperator);
                    this.rights = mapReduceKeysToLongTask;
                    mapReduceKeysToLongTask.fork();
                }
                while ((mapReduceKeysToLongTask = this.advance()) != null) {
                    l = longBinaryOperator.applyAsLong(l, countedCompleter.applyAsLong(((Node)mapReduceKeysToLongTask).key));
                }
                this.result = l;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    MapReduceKeysToLongTask mapReduceKeysToLongTask2 = (MapReduceKeysToLongTask)countedCompleter;
                    mapReduceKeysToLongTask = mapReduceKeysToLongTask2.rights;
                    while (mapReduceKeysToLongTask != null) {
                        mapReduceKeysToLongTask2.result = longBinaryOperator.applyAsLong(mapReduceKeysToLongTask2.result, mapReduceKeysToLongTask.result);
                        mapReduceKeysToLongTask = mapReduceKeysToLongTask.nextRight;
                        mapReduceKeysToLongTask2.rights = mapReduceKeysToLongTask;
                    }
                }
            }
        }

        @Override
        public final Long getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceMappingsTask<K, V, U>
    extends BulkTask<K, V, U> {
        MapReduceMappingsTask<K, V, U> nextRight;
        final BiFunction<? super U, ? super U, ? extends U> reducer;
        U result;
        MapReduceMappingsTask<K, V, U> rights;
        final BiFunction<? super K, ? super V, ? extends U> transformer;

        MapReduceMappingsTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceMappingsTask<K, V, U> mapReduceMappingsTask, BiFunction<? super K, ? super V, ? extends U> biFunction, BiFunction<? super U, ? super U, ? extends U> biFunction2) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceMappingsTask;
            this.transformer = biFunction;
            this.reducer = biFunction2;
        }

        @Override
        public final void compute() {
            BiFunction<U, U, U> biFunction;
            BiFunction biFunction2 = this.transformer;
            if (biFunction2 != null && (biFunction = this.reducer) != null) {
                U u;
                int n;
                int n2;
                CountedCompleter<Object> countedCompleter;
                MapReduceMappingsTask<K, V, U> mapReduceMappingsTask;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    countedCompleter = new MapReduceMappingsTask(this, n4, n2, n, this.tab, this.rights, biFunction2, biFunction);
                    this.rights = countedCompleter;
                    countedCompleter.fork();
                }
                countedCompleter = null;
                while ((mapReduceMappingsTask = this.advance()) != null) {
                    u = biFunction2.apply(((Node)mapReduceMappingsTask).key, ((Node)mapReduceMappingsTask).val);
                    mapReduceMappingsTask = countedCompleter;
                    if (u != null) {
                        countedCompleter = countedCompleter == null ? u : biFunction.apply(countedCompleter, u);
                        mapReduceMappingsTask = countedCompleter;
                    }
                    countedCompleter = mapReduceMappingsTask;
                }
                this.result = countedCompleter;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    biFunction2 = (MapReduceMappingsTask)countedCompleter;
                    mapReduceMappingsTask = ((MapReduceMappingsTask)biFunction2).rights;
                    while (mapReduceMappingsTask != null) {
                        u = mapReduceMappingsTask.result;
                        if (u != null) {
                            U u2 = ((MapReduceMappingsTask)biFunction2).result;
                            if (u2 != null) {
                                u = biFunction.apply(u2, u);
                            }
                            ((MapReduceMappingsTask)biFunction2).result = u;
                        }
                        mapReduceMappingsTask = mapReduceMappingsTask.nextRight;
                        ((MapReduceMappingsTask)biFunction2).rights = mapReduceMappingsTask;
                    }
                }
            }
        }

        @Override
        public final U getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceMappingsToDoubleTask<K, V>
    extends BulkTask<K, V, Double> {
        final double basis;
        MapReduceMappingsToDoubleTask<K, V> nextRight;
        final DoubleBinaryOperator reducer;
        double result;
        MapReduceMappingsToDoubleTask<K, V> rights;
        final ToDoubleBiFunction<? super K, ? super V> transformer;

        MapReduceMappingsToDoubleTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceMappingsToDoubleTask<K, V> mapReduceMappingsToDoubleTask, ToDoubleBiFunction<? super K, ? super V> toDoubleBiFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceMappingsToDoubleTask;
            this.transformer = toDoubleBiFunction;
            this.basis = d;
            this.reducer = doubleBinaryOperator;
        }

        @Override
        public final void compute() {
            DoubleBinaryOperator doubleBinaryOperator;
            MapReduceMappingsToDoubleTask<K, V> mapReduceMappingsToDoubleTask = this.transformer;
            CountedCompleter<?> countedCompleter = mapReduceMappingsToDoubleTask;
            if (mapReduceMappingsToDoubleTask != null && (doubleBinaryOperator = this.reducer) != null) {
                int n;
                int n2;
                double d = this.basis;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    mapReduceMappingsToDoubleTask = new MapReduceMappingsToDoubleTask<K, V>(this, n4, n, n2, this.tab, this.rights, (ToDoubleBiFunction<K, V>)((Object)countedCompleter), d, doubleBinaryOperator);
                    this.rights = mapReduceMappingsToDoubleTask;
                    mapReduceMappingsToDoubleTask.fork();
                }
                while ((mapReduceMappingsToDoubleTask = this.advance()) != null) {
                    d = doubleBinaryOperator.applyAsDouble(d, countedCompleter.applyAsDouble(((Node)mapReduceMappingsToDoubleTask).key, ((Node)mapReduceMappingsToDoubleTask).val));
                }
                this.result = d;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    MapReduceMappingsToDoubleTask mapReduceMappingsToDoubleTask2 = (MapReduceMappingsToDoubleTask)countedCompleter;
                    mapReduceMappingsToDoubleTask = mapReduceMappingsToDoubleTask2.rights;
                    while (mapReduceMappingsToDoubleTask != null) {
                        mapReduceMappingsToDoubleTask2.result = doubleBinaryOperator.applyAsDouble(mapReduceMappingsToDoubleTask2.result, mapReduceMappingsToDoubleTask.result);
                        mapReduceMappingsToDoubleTask = mapReduceMappingsToDoubleTask.nextRight;
                        mapReduceMappingsToDoubleTask2.rights = mapReduceMappingsToDoubleTask;
                    }
                }
            }
        }

        @Override
        public final Double getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceMappingsToIntTask<K, V>
    extends BulkTask<K, V, Integer> {
        final int basis;
        MapReduceMappingsToIntTask<K, V> nextRight;
        final IntBinaryOperator reducer;
        int result;
        MapReduceMappingsToIntTask<K, V> rights;
        final ToIntBiFunction<? super K, ? super V> transformer;

        MapReduceMappingsToIntTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceMappingsToIntTask<K, V> mapReduceMappingsToIntTask, ToIntBiFunction<? super K, ? super V> toIntBiFunction, int n4, IntBinaryOperator intBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceMappingsToIntTask;
            this.transformer = toIntBiFunction;
            this.basis = n4;
            this.reducer = intBinaryOperator;
        }

        @Override
        public final void compute() {
            IntBinaryOperator intBinaryOperator;
            Object object = this.transformer;
            if (object != null && (intBinaryOperator = this.reducer) != null) {
                int n;
                int n2;
                MapReduceMappingsToIntTask<K, V> mapReduceMappingsToIntTask;
                int n3 = this.basis;
                int n4 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n4 >>> 1) > n4) {
                    int n5;
                    this.addToPendingCount(1);
                    this.batch = n5 = this.batch >>> 1;
                    this.baseLimit = n2;
                    mapReduceMappingsToIntTask = new MapReduceMappingsToIntTask<K, V>(this, n5, n2, n, this.tab, this.rights, (ToIntBiFunction<K, V>)object, n3, intBinaryOperator);
                    this.rights = mapReduceMappingsToIntTask;
                    mapReduceMappingsToIntTask.fork();
                }
                while ((mapReduceMappingsToIntTask = this.advance()) != null) {
                    n3 = intBinaryOperator.applyAsInt(n3, object.applyAsInt(((Node)mapReduceMappingsToIntTask).key, ((Node)mapReduceMappingsToIntTask).val));
                }
                this.result = n3;
                for (object = this.firstComplete(); object != null; object = object.nextComplete()) {
                    MapReduceMappingsToIntTask mapReduceMappingsToIntTask2 = (MapReduceMappingsToIntTask)object;
                    mapReduceMappingsToIntTask = mapReduceMappingsToIntTask2.rights;
                    while (mapReduceMappingsToIntTask != null) {
                        mapReduceMappingsToIntTask2.result = intBinaryOperator.applyAsInt(mapReduceMappingsToIntTask2.result, mapReduceMappingsToIntTask.result);
                        mapReduceMappingsToIntTask = mapReduceMappingsToIntTask.nextRight;
                        mapReduceMappingsToIntTask2.rights = mapReduceMappingsToIntTask;
                    }
                }
            }
        }

        @Override
        public final Integer getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceMappingsToLongTask<K, V>
    extends BulkTask<K, V, Long> {
        final long basis;
        MapReduceMappingsToLongTask<K, V> nextRight;
        final LongBinaryOperator reducer;
        long result;
        MapReduceMappingsToLongTask<K, V> rights;
        final ToLongBiFunction<? super K, ? super V> transformer;

        MapReduceMappingsToLongTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceMappingsToLongTask<K, V> mapReduceMappingsToLongTask, ToLongBiFunction<? super K, ? super V> toLongBiFunction, long l, LongBinaryOperator longBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceMappingsToLongTask;
            this.transformer = toLongBiFunction;
            this.basis = l;
            this.reducer = longBinaryOperator;
        }

        @Override
        public final void compute() {
            LongBinaryOperator longBinaryOperator;
            MapReduceMappingsToLongTask<K, V> mapReduceMappingsToLongTask = this.transformer;
            CountedCompleter<?> countedCompleter = mapReduceMappingsToLongTask;
            if (mapReduceMappingsToLongTask != null && (longBinaryOperator = this.reducer) != null) {
                int n;
                int n2;
                long l = this.basis;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    mapReduceMappingsToLongTask = new MapReduceMappingsToLongTask<K, V>(this, n4, n, n2, this.tab, this.rights, (ToLongBiFunction<K, V>)((Object)countedCompleter), l, longBinaryOperator);
                    this.rights = mapReduceMappingsToLongTask;
                    mapReduceMappingsToLongTask.fork();
                }
                while ((mapReduceMappingsToLongTask = this.advance()) != null) {
                    l = longBinaryOperator.applyAsLong(l, countedCompleter.applyAsLong(((Node)mapReduceMappingsToLongTask).key, ((Node)mapReduceMappingsToLongTask).val));
                }
                this.result = l;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    MapReduceMappingsToLongTask mapReduceMappingsToLongTask2 = (MapReduceMappingsToLongTask)countedCompleter;
                    mapReduceMappingsToLongTask = mapReduceMappingsToLongTask2.rights;
                    while (mapReduceMappingsToLongTask != null) {
                        mapReduceMappingsToLongTask2.result = longBinaryOperator.applyAsLong(mapReduceMappingsToLongTask2.result, mapReduceMappingsToLongTask.result);
                        mapReduceMappingsToLongTask = mapReduceMappingsToLongTask.nextRight;
                        mapReduceMappingsToLongTask2.rights = mapReduceMappingsToLongTask;
                    }
                }
            }
        }

        @Override
        public final Long getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceValuesTask<K, V, U>
    extends BulkTask<K, V, U> {
        MapReduceValuesTask<K, V, U> nextRight;
        final BiFunction<? super U, ? super U, ? extends U> reducer;
        U result;
        MapReduceValuesTask<K, V, U> rights;
        final Function<? super V, ? extends U> transformer;

        MapReduceValuesTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceValuesTask<K, V, U> mapReduceValuesTask, Function<? super V, ? extends U> function, BiFunction<? super U, ? super U, ? extends U> biFunction) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceValuesTask;
            this.transformer = function;
            this.reducer = biFunction;
        }

        @Override
        public final void compute() {
            BiFunction<U, U, U> biFunction;
            Function<V, U> function = this.transformer;
            if (function != null && (biFunction = this.reducer) != null) {
                U u;
                int n;
                int n2;
                CountedCompleter<Object> countedCompleter;
                MapReduceValuesTask<K, V, U> mapReduceValuesTask;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    countedCompleter = new MapReduceValuesTask(this, n4, n2, n, this.tab, this.rights, function, biFunction);
                    this.rights = countedCompleter;
                    countedCompleter.fork();
                }
                countedCompleter = null;
                while ((mapReduceValuesTask = this.advance()) != null) {
                    u = function.apply(((Node)mapReduceValuesTask).val);
                    mapReduceValuesTask = countedCompleter;
                    if (u != null) {
                        countedCompleter = countedCompleter == null ? u : biFunction.apply(countedCompleter, u);
                        mapReduceValuesTask = countedCompleter;
                    }
                    countedCompleter = mapReduceValuesTask;
                }
                this.result = countedCompleter;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    function = (MapReduceValuesTask)countedCompleter;
                    mapReduceValuesTask = ((MapReduceValuesTask)function).rights;
                    while (mapReduceValuesTask != null) {
                        u = mapReduceValuesTask.result;
                        if (u != null) {
                            U u2 = ((MapReduceValuesTask)function).result;
                            if (u2 != null) {
                                u = biFunction.apply(u2, u);
                            }
                            ((MapReduceValuesTask)function).result = u;
                        }
                        mapReduceValuesTask = mapReduceValuesTask.nextRight;
                        ((MapReduceValuesTask)function).rights = mapReduceValuesTask;
                    }
                }
            }
        }

        @Override
        public final U getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceValuesToDoubleTask<K, V>
    extends BulkTask<K, V, Double> {
        final double basis;
        MapReduceValuesToDoubleTask<K, V> nextRight;
        final DoubleBinaryOperator reducer;
        double result;
        MapReduceValuesToDoubleTask<K, V> rights;
        final ToDoubleFunction<? super V> transformer;

        MapReduceValuesToDoubleTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceValuesToDoubleTask<K, V> mapReduceValuesToDoubleTask, ToDoubleFunction<? super V> toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceValuesToDoubleTask;
            this.transformer = toDoubleFunction;
            this.basis = d;
            this.reducer = doubleBinaryOperator;
        }

        @Override
        public final void compute() {
            DoubleBinaryOperator doubleBinaryOperator;
            MapReduceValuesToDoubleTask<K, V> mapReduceValuesToDoubleTask = this.transformer;
            CountedCompleter<?> countedCompleter = mapReduceValuesToDoubleTask;
            if (mapReduceValuesToDoubleTask != null && (doubleBinaryOperator = this.reducer) != null) {
                int n;
                int n2;
                double d = this.basis;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    mapReduceValuesToDoubleTask = new MapReduceValuesToDoubleTask<K, V>(this, n4, n, n2, this.tab, this.rights, (ToDoubleFunction<V>)((Object)countedCompleter), d, doubleBinaryOperator);
                    this.rights = mapReduceValuesToDoubleTask;
                    mapReduceValuesToDoubleTask.fork();
                }
                while ((mapReduceValuesToDoubleTask = this.advance()) != null) {
                    d = doubleBinaryOperator.applyAsDouble(d, countedCompleter.applyAsDouble(((Node)mapReduceValuesToDoubleTask).val));
                }
                this.result = d;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    MapReduceValuesToDoubleTask mapReduceValuesToDoubleTask2 = (MapReduceValuesToDoubleTask)countedCompleter;
                    mapReduceValuesToDoubleTask = mapReduceValuesToDoubleTask2.rights;
                    while (mapReduceValuesToDoubleTask != null) {
                        mapReduceValuesToDoubleTask2.result = doubleBinaryOperator.applyAsDouble(mapReduceValuesToDoubleTask2.result, mapReduceValuesToDoubleTask.result);
                        mapReduceValuesToDoubleTask = mapReduceValuesToDoubleTask.nextRight;
                        mapReduceValuesToDoubleTask2.rights = mapReduceValuesToDoubleTask;
                    }
                }
            }
        }

        @Override
        public final Double getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceValuesToIntTask<K, V>
    extends BulkTask<K, V, Integer> {
        final int basis;
        MapReduceValuesToIntTask<K, V> nextRight;
        final IntBinaryOperator reducer;
        int result;
        MapReduceValuesToIntTask<K, V> rights;
        final ToIntFunction<? super V> transformer;

        MapReduceValuesToIntTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceValuesToIntTask<K, V> mapReduceValuesToIntTask, ToIntFunction<? super V> toIntFunction, int n4, IntBinaryOperator intBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceValuesToIntTask;
            this.transformer = toIntFunction;
            this.basis = n4;
            this.reducer = intBinaryOperator;
        }

        @Override
        public final void compute() {
            IntBinaryOperator intBinaryOperator;
            Object object = this.transformer;
            if (object != null && (intBinaryOperator = this.reducer) != null) {
                int n;
                MapReduceValuesToIntTask<K, V> mapReduceValuesToIntTask;
                int n2;
                int n3 = this.basis;
                int n4 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n4 >>> 1) > n4) {
                    int n5;
                    this.addToPendingCount(1);
                    this.batch = n5 = this.batch >>> 1;
                    this.baseLimit = n2;
                    mapReduceValuesToIntTask = new MapReduceValuesToIntTask<K, V>(this, n5, n2, n, this.tab, this.rights, (ToIntFunction<V>)object, n3, intBinaryOperator);
                    this.rights = mapReduceValuesToIntTask;
                    mapReduceValuesToIntTask.fork();
                }
                while ((mapReduceValuesToIntTask = this.advance()) != null) {
                    n3 = intBinaryOperator.applyAsInt(n3, object.applyAsInt(((Node)mapReduceValuesToIntTask).val));
                }
                this.result = n3;
                for (object = this.firstComplete(); object != null; object = object.nextComplete()) {
                    MapReduceValuesToIntTask mapReduceValuesToIntTask2 = (MapReduceValuesToIntTask)object;
                    mapReduceValuesToIntTask = mapReduceValuesToIntTask2.rights;
                    while (mapReduceValuesToIntTask != null) {
                        mapReduceValuesToIntTask2.result = intBinaryOperator.applyAsInt(mapReduceValuesToIntTask2.result, mapReduceValuesToIntTask.result);
                        mapReduceValuesToIntTask = mapReduceValuesToIntTask.nextRight;
                        mapReduceValuesToIntTask2.rights = mapReduceValuesToIntTask;
                    }
                }
            }
        }

        @Override
        public final Integer getRawResult() {
            return this.result;
        }
    }

    static final class MapReduceValuesToLongTask<K, V>
    extends BulkTask<K, V, Long> {
        final long basis;
        MapReduceValuesToLongTask<K, V> nextRight;
        final LongBinaryOperator reducer;
        long result;
        MapReduceValuesToLongTask<K, V> rights;
        final ToLongFunction<? super V> transformer;

        MapReduceValuesToLongTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, MapReduceValuesToLongTask<K, V> mapReduceValuesToLongTask, ToLongFunction<? super V> toLongFunction, long l, LongBinaryOperator longBinaryOperator) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = mapReduceValuesToLongTask;
            this.transformer = toLongFunction;
            this.basis = l;
            this.reducer = longBinaryOperator;
        }

        @Override
        public final void compute() {
            LongBinaryOperator longBinaryOperator;
            MapReduceValuesToLongTask<K, V> mapReduceValuesToLongTask = this.transformer;
            CountedCompleter<?> countedCompleter = mapReduceValuesToLongTask;
            if (mapReduceValuesToLongTask != null && (longBinaryOperator = this.reducer) != null) {
                int n;
                int n2;
                long l = this.basis;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    mapReduceValuesToLongTask = new MapReduceValuesToLongTask<K, V>(this, n4, n, n2, this.tab, this.rights, (ToLongFunction<V>)((Object)countedCompleter), l, longBinaryOperator);
                    this.rights = mapReduceValuesToLongTask;
                    mapReduceValuesToLongTask.fork();
                }
                while ((mapReduceValuesToLongTask = this.advance()) != null) {
                    l = longBinaryOperator.applyAsLong(l, countedCompleter.applyAsLong(((Node)mapReduceValuesToLongTask).val));
                }
                this.result = l;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    MapReduceValuesToLongTask mapReduceValuesToLongTask2 = (MapReduceValuesToLongTask)countedCompleter;
                    mapReduceValuesToLongTask = mapReduceValuesToLongTask2.rights;
                    while (mapReduceValuesToLongTask != null) {
                        mapReduceValuesToLongTask2.result = longBinaryOperator.applyAsLong(mapReduceValuesToLongTask2.result, mapReduceValuesToLongTask.result);
                        mapReduceValuesToLongTask = mapReduceValuesToLongTask.nextRight;
                        mapReduceValuesToLongTask2.rights = mapReduceValuesToLongTask;
                    }
                }
            }
        }

        @Override
        public final Long getRawResult() {
            return this.result;
        }
    }

    static class Node<K, V>
    implements Map.Entry<K, V> {
        final int hash;
        final K key;
        volatile Node<K, V> next;
        volatile V val;

        Node(int n, K k, V v, Node<K, V> node) {
            this.hash = n;
            this.key = k;
            this.val = v;
            this.next = node;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public final boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) return false;
            Map.Entry entry = (Map.Entry)object;
            object = entry.getKey();
            if (object == null) return false;
            if ((entry = entry.getValue()) == null) return false;
            K k = this.key;
            if (object != k) {
                if (!object.equals(k)) return false;
            }
            if (entry == (object = this.val)) return true;
            if (!((Object)entry).equals(object)) return false;
            return true;
        }

        Node<K, V> find(int n, Object object) {
            Node<K, V> node = this;
            if (object != null) {
                Object object2;
                do {
                    if (node.hash == n && ((object2 = node.key) == object || object2 != null && object.equals(object2))) {
                        return node;
                    }
                    object2 = node.next;
                    node = object2;
                } while (object2 != null);
            }
            return null;
        }

        @Override
        public final K getKey() {
            return this.key;
        }

        @Override
        public final V getValue() {
            return this.val;
        }

        @Override
        public final int hashCode() {
            return this.key.hashCode() ^ this.val.hashCode();
        }

        @Override
        public final V setValue(V v) {
            throw new UnsupportedOperationException();
        }

        public final String toString() {
            return Helpers.mapEntryToString(this.key, this.val);
        }
    }

    static final class ReduceEntriesTask<K, V>
    extends BulkTask<K, V, Map.Entry<K, V>> {
        ReduceEntriesTask<K, V> nextRight;
        final BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer;
        Map.Entry<K, V> result;
        ReduceEntriesTask<K, V> rights;

        ReduceEntriesTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, ReduceEntriesTask<K, V> reduceEntriesTask, BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> biFunction) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = reduceEntriesTask;
            this.reducer = biFunction;
        }

        @Override
        public final void compute() {
            BiFunction<Map.Entry<K, V>, Map.Entry<K, V>, Map.Entry<K, V>> biFunction = this.reducer;
            if (biFunction != null) {
                ReduceEntriesTask<K, V> reduceEntriesTask;
                int n;
                int n2;
                ReduceEntriesTask<K, V> reduceEntriesTask2;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    reduceEntriesTask2 = new ReduceEntriesTask<K, V>(this, n4, n, n2, this.tab, this.rights, biFunction);
                    this.rights = reduceEntriesTask2;
                    reduceEntriesTask2.fork();
                }
                reduceEntriesTask2 = null;
                while ((reduceEntriesTask = this.advance()) != null) {
                    if (reduceEntriesTask2 == null) {
                        reduceEntriesTask2 = reduceEntriesTask;
                        continue;
                    }
                    reduceEntriesTask2 = biFunction.apply((Map.Entry<K, V>)((Object)reduceEntriesTask2), (Map.Entry<K, V>)((Object)reduceEntriesTask));
                }
                this.result = reduceEntriesTask2;
                for (reduceEntriesTask2 = this.firstComplete(); reduceEntriesTask2 != null; reduceEntriesTask2 = reduceEntriesTask2.nextComplete()) {
                    ReduceEntriesTask reduceEntriesTask3 = reduceEntriesTask2;
                    reduceEntriesTask = reduceEntriesTask3.rights;
                    while (reduceEntriesTask != null) {
                        Map.Entry<K, V> entry = reduceEntriesTask.result;
                        if (entry != null) {
                            Map.Entry<K, V> entry2 = reduceEntriesTask3.result;
                            if (entry2 != null) {
                                entry = biFunction.apply(entry2, entry);
                            }
                            reduceEntriesTask3.result = entry;
                        }
                        reduceEntriesTask = reduceEntriesTask.nextRight;
                        reduceEntriesTask3.rights = reduceEntriesTask;
                    }
                }
            }
        }

        @Override
        public final Map.Entry<K, V> getRawResult() {
            return this.result;
        }
    }

    static final class ReduceKeysTask<K, V>
    extends BulkTask<K, V, K> {
        ReduceKeysTask<K, V> nextRight;
        final BiFunction<? super K, ? super K, ? extends K> reducer;
        K result;
        ReduceKeysTask<K, V> rights;

        ReduceKeysTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, ReduceKeysTask<K, V> reduceKeysTask, BiFunction<? super K, ? super K, ? extends K> biFunction) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = reduceKeysTask;
            this.reducer = biFunction;
        }

        @Override
        public final void compute() {
            BiFunction<K, K, K> biFunction = this.reducer;
            if (biFunction != null) {
                CountedCompleter<?> countedCompleter;
                int n;
                int n2;
                ReduceKeysTask<K, V> reduceKeysTask;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    countedCompleter = new ReduceKeysTask(this, n4, n, n2, this.tab, this.rights, biFunction);
                    this.rights = countedCompleter;
                    countedCompleter.fork();
                }
                countedCompleter = null;
                while ((reduceKeysTask = this.advance()) != null) {
                    reduceKeysTask = ((Node)reduceKeysTask).key;
                    if (countedCompleter == null) {
                        countedCompleter = reduceKeysTask;
                        continue;
                    }
                    if (reduceKeysTask == null) continue;
                    countedCompleter = biFunction.apply(countedCompleter, reduceKeysTask);
                }
                this.result = countedCompleter;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    ReduceKeysTask reduceKeysTask2 = (ReduceKeysTask)countedCompleter;
                    reduceKeysTask = reduceKeysTask2.rights;
                    while (reduceKeysTask != null) {
                        K k = reduceKeysTask.result;
                        if (k != null) {
                            K k2 = reduceKeysTask2.result;
                            if (k2 != null) {
                                k = biFunction.apply(k2, k);
                            }
                            reduceKeysTask2.result = k;
                        }
                        reduceKeysTask = reduceKeysTask.nextRight;
                        reduceKeysTask2.rights = reduceKeysTask;
                    }
                }
            }
        }

        @Override
        public final K getRawResult() {
            return this.result;
        }
    }

    static final class ReduceValuesTask<K, V>
    extends BulkTask<K, V, V> {
        ReduceValuesTask<K, V> nextRight;
        final BiFunction<? super V, ? super V, ? extends V> reducer;
        V result;
        ReduceValuesTask<K, V> rights;

        ReduceValuesTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, ReduceValuesTask<K, V> reduceValuesTask, BiFunction<? super V, ? super V, ? extends V> biFunction) {
            super(bulkTask, n, n2, n3, arrnode);
            this.nextRight = reduceValuesTask;
            this.reducer = biFunction;
        }

        @Override
        public final void compute() {
            BiFunction<V, V, V> biFunction = this.reducer;
            if (biFunction != null) {
                CountedCompleter<?> countedCompleter;
                int n;
                ReduceValuesTask<K, V> reduceValuesTask;
                int n2;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n = (n2 = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n;
                    countedCompleter = new ReduceValuesTask(this, n4, n, n2, this.tab, this.rights, biFunction);
                    this.rights = countedCompleter;
                    countedCompleter.fork();
                }
                countedCompleter = null;
                while ((reduceValuesTask = this.advance()) != null) {
                    reduceValuesTask = ((Node)reduceValuesTask).val;
                    if (countedCompleter == null) {
                        countedCompleter = reduceValuesTask;
                        continue;
                    }
                    countedCompleter = biFunction.apply(countedCompleter, reduceValuesTask);
                }
                this.result = countedCompleter;
                for (countedCompleter = this.firstComplete(); countedCompleter != null; countedCompleter = countedCompleter.nextComplete()) {
                    ReduceValuesTask reduceValuesTask2 = (ReduceValuesTask)countedCompleter;
                    reduceValuesTask = reduceValuesTask2.rights;
                    while (reduceValuesTask != null) {
                        V v = reduceValuesTask.result;
                        if (v != null) {
                            V v2 = reduceValuesTask2.result;
                            if (v2 != null) {
                                v = biFunction.apply(v2, v);
                            }
                            reduceValuesTask2.result = v;
                        }
                        reduceValuesTask = reduceValuesTask.nextRight;
                        reduceValuesTask2.rights = reduceValuesTask;
                    }
                }
            }
        }

        @Override
        public final V getRawResult() {
            return this.result;
        }
    }

    static final class ReservationNode<K, V>
    extends Node<K, V> {
        ReservationNode() {
            super(-3, null, null, null);
        }

        @Override
        Node<K, V> find(int n, Object object) {
            return null;
        }
    }

    static final class SearchEntriesTask<K, V, U>
    extends BulkTask<K, V, U> {
        final AtomicReference<U> result;
        final Function<Map.Entry<K, V>, ? extends U> searchFunction;

        SearchEntriesTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, Function<Map.Entry<K, V>, ? extends U> function, AtomicReference<U> atomicReference) {
            super(bulkTask, n, n2, n3, arrnode);
            this.searchFunction = function;
            this.result = atomicReference;
        }

        @Override
        public final void compute() {
            AtomicReference<U> atomicReference;
            Function<Map.Entry<K, V>, U> function = this.searchFunction;
            if (function != null && (atomicReference = this.result) != null) {
                int n;
                int n2;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    if (atomicReference.get() != null) {
                        return;
                    }
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    new SearchEntriesTask<K, V, U>(this, n4, n2, n, this.tab, function, atomicReference).fork();
                }
                while (atomicReference.get() == null) {
                    Node node = this.advance();
                    if (node == null) {
                        this.propagateCompletion();
                        break;
                    }
                    if ((node = function.apply(node)) == null) continue;
                    if (atomicReference.compareAndSet(null, node)) {
                        this.quietlyCompleteRoot();
                    }
                    return;
                }
            }
        }

        @Override
        public final U getRawResult() {
            return this.result.get();
        }
    }

    static final class SearchKeysTask<K, V, U>
    extends BulkTask<K, V, U> {
        final AtomicReference<U> result;
        final Function<? super K, ? extends U> searchFunction;

        SearchKeysTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, Function<? super K, ? extends U> function, AtomicReference<U> atomicReference) {
            super(bulkTask, n, n2, n3, arrnode);
            this.searchFunction = function;
            this.result = atomicReference;
        }

        @Override
        public final void compute() {
            AtomicReference<U> atomicReference;
            Function<K, U> function = this.searchFunction;
            if (function != null && (atomicReference = this.result) != null) {
                int n;
                int n2;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    if (atomicReference.get() != null) {
                        return;
                    }
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    new SearchKeysTask<K, V, U>(this, n4, n2, n, this.tab, function, atomicReference).fork();
                }
                while (atomicReference.get() == null) {
                    Node node = this.advance();
                    if (node == null) {
                        this.propagateCompletion();
                        break;
                    }
                    node = function.apply(node.key);
                    if (node == null) continue;
                    if (!atomicReference.compareAndSet(null, node)) break;
                    this.quietlyCompleteRoot();
                    break;
                }
            }
        }

        @Override
        public final U getRawResult() {
            return this.result.get();
        }
    }

    static final class SearchMappingsTask<K, V, U>
    extends BulkTask<K, V, U> {
        final AtomicReference<U> result;
        final BiFunction<? super K, ? super V, ? extends U> searchFunction;

        SearchMappingsTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, BiFunction<? super K, ? super V, ? extends U> biFunction, AtomicReference<U> atomicReference) {
            super(bulkTask, n, n2, n3, arrnode);
            this.searchFunction = biFunction;
            this.result = atomicReference;
        }

        @Override
        public final void compute() {
            AtomicReference<U> atomicReference;
            BiFunction biFunction = this.searchFunction;
            if (biFunction != null && (atomicReference = this.result) != null) {
                int n;
                int n2;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    if (atomicReference.get() != null) {
                        return;
                    }
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    new SearchMappingsTask<K, V, U>(this, n4, n2, n, this.tab, biFunction, atomicReference).fork();
                }
                while (atomicReference.get() == null) {
                    Node node = this.advance();
                    if (node == null) {
                        this.propagateCompletion();
                        break;
                    }
                    node = biFunction.apply(node.key, node.val);
                    if (node == null) continue;
                    if (!atomicReference.compareAndSet(null, node)) break;
                    this.quietlyCompleteRoot();
                    break;
                }
            }
        }

        @Override
        public final U getRawResult() {
            return this.result.get();
        }
    }

    static final class SearchValuesTask<K, V, U>
    extends BulkTask<K, V, U> {
        final AtomicReference<U> result;
        final Function<? super V, ? extends U> searchFunction;

        SearchValuesTask(BulkTask<K, V, ?> bulkTask, int n, int n2, int n3, Node<K, V>[] arrnode, Function<? super V, ? extends U> function, AtomicReference<U> atomicReference) {
            super(bulkTask, n, n2, n3, arrnode);
            this.searchFunction = function;
            this.result = atomicReference;
        }

        @Override
        public final void compute() {
            AtomicReference<U> atomicReference;
            Function<V, U> function = this.searchFunction;
            if (function != null && (atomicReference = this.result) != null) {
                int n;
                int n2;
                int n3 = this.baseIndex;
                while (this.batch > 0 && (n2 = (n = this.baseLimit) + n3 >>> 1) > n3) {
                    int n4;
                    if (atomicReference.get() != null) {
                        return;
                    }
                    this.addToPendingCount(1);
                    this.batch = n4 = this.batch >>> 1;
                    this.baseLimit = n2;
                    new SearchValuesTask<K, V, U>(this, n4, n2, n, this.tab, function, atomicReference).fork();
                }
                while (atomicReference.get() == null) {
                    Node node = this.advance();
                    if (node == null) {
                        this.propagateCompletion();
                        break;
                    }
                    node = function.apply(node.val);
                    if (node == null) continue;
                    if (!atomicReference.compareAndSet(null, node)) break;
                    this.quietlyCompleteRoot();
                    break;
                }
            }
        }

        @Override
        public final U getRawResult() {
            return this.result.get();
        }
    }

    static class Segment<K, V>
    extends ReentrantLock
    implements Serializable {
        private static final long serialVersionUID = 2249069246763182397L;
        final float loadFactor;

        Segment(float f) {
            this.loadFactor = f;
        }
    }

    static final class TableStack<K, V> {
        int index;
        int length;
        TableStack<K, V> next;
        Node<K, V>[] tab;

        TableStack() {
        }
    }

    static class Traverser<K, V> {
        int baseIndex;
        int baseLimit;
        final int baseSize;
        int index;
        Node<K, V> next;
        TableStack<K, V> spare;
        TableStack<K, V> stack;
        Node<K, V>[] tab;

        Traverser(Node<K, V>[] arrnode, int n, int n2, int n3) {
            this.tab = arrnode;
            this.baseSize = n;
            this.index = n2;
            this.baseIndex = n2;
            this.baseLimit = n3;
            this.next = null;
        }

        private void pushState(Node<K, V>[] arrnode, int n, int n2) {
            TableStack<K, V> tableStack = this.spare;
            if (tableStack != null) {
                this.spare = tableStack.next;
            } else {
                tableStack = new TableStack();
            }
            tableStack.tab = arrnode;
            tableStack.length = n2;
            tableStack.index = n;
            tableStack.next = this.stack;
            this.stack = tableStack;
        }

        private void recoverState(int n) {
            int n2;
            TableStack<K, V> tableStack;
            while ((tableStack = this.stack) != null) {
                int n3 = this.index;
                n2 = tableStack.length;
                this.index = n3 += n2;
                if (n3 < n) break;
                n = n2;
                this.index = tableStack.index;
                this.tab = tableStack.tab;
                tableStack.tab = null;
                TableStack tableStack2 = tableStack.next;
                tableStack.next = this.spare;
                this.stack = tableStack2;
                this.spare = tableStack;
            }
            if (tableStack == null) {
                this.index = n2 = this.index + this.baseSize;
                if (n2 >= n) {
                    this.baseIndex = n = this.baseIndex + 1;
                    this.index = n;
                }
            }
        }

        final Node<K, V> advance() {
            Node<K, V> node;
            Node<K, V> node2;
            Node<K, V> node3 = node2 = (node = this.next);
            if (node != null) {
                node3 = node2.next;
            }
            do {
                int n;
                int n2;
                Node<K, V>[] arrnode;
                if (node3 != null) {
                    this.next = node3;
                    return node3;
                }
                if (this.baseIndex >= this.baseLimit || (arrnode = this.tab) == null || (n = arrnode.length) <= (n2 = this.index) || n2 < 0) break;
                node3 = node2 = (node = ConcurrentHashMap.tabAt(arrnode, n2));
                if (node != null) {
                    node3 = node2;
                    if (node2.hash < 0) {
                        if (node2 instanceof ForwardingNode) {
                            this.tab = ((ForwardingNode)node2).nextTable;
                            node3 = null;
                            this.pushState(arrnode, n2, n);
                            continue;
                        }
                        node3 = node2 instanceof TreeBin ? ((TreeBin)node2).first : null;
                    }
                }
                if (this.stack != null) {
                    this.recoverState(n);
                    continue;
                }
                this.index = n2 = this.baseSize + n2;
                if (n2 < n) continue;
                this.baseIndex = n = this.baseIndex + 1;
                this.index = n;
            } while (true);
            this.next = null;
            return null;
        }
    }

    static final class TreeBin<K, V>
    extends Node<K, V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long LOCKSTATE;
        static final int READER = 4;
        private static final Unsafe U;
        static final int WAITER = 2;
        static final int WRITER = 1;
        volatile TreeNode<K, V> first;
        volatile int lockState;
        TreeNode<K, V> root;
        volatile Thread waiter;

        static {
            U = Unsafe.getUnsafe();
            try {
                LOCKSTATE = U.objectFieldOffset(TreeBin.class.getDeclaredField("lockState"));
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new Error(reflectiveOperationException);
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        TreeBin(TreeNode<K, V> var1_1) {
            super(-2, null, null, null);
            this.first = var1_1;
            var2_2 = null;
            var3_3 = var1_1;
            block0 : do {
                block11 : {
                    if (var3_3 == null) {
                        this.root = var2_2;
                        return;
                    }
                    var4_4 = (TreeNode<K, V>)var3_3.next;
                    var3_3.right = null;
                    var3_3.left = null;
                    if (var2_2 != null) break block11;
                    var3_3.parent = null;
                    var3_3.red = false;
                    var2_2 = var3_3;
                    ** GOTO lbl55
                }
                var5_5 = var3_3.key;
                var6_6 = var3_3.hash;
                var7_7 = null;
                var1_1 = var2_2;
                do {
                    block17 : {
                        block13 : {
                            block16 : {
                                block15 : {
                                    block14 : {
                                        block12 : {
                                            var8_8 = var1_1;
                                            var9_9 = var8_8.key;
                                            var10_10 = var8_8.hash;
                                            if (var10_10 <= var6_6) break block12;
                                            var10_10 = -1;
                                            var1_1 = var7_7;
                                            break block13;
                                        }
                                        if (var10_10 >= var6_6) break block14;
                                        var10_10 = 1;
                                        var1_1 = var7_7;
                                        break block13;
                                    }
                                    var1_1 = var7_7;
                                    if (var7_7 != null) break block15;
                                    var1_1 = var7_7 = (var11_11 = ConcurrentHashMap.comparableClassFor(var5_5));
                                    if (var11_11 == null) break block16;
                                    var1_1 = var7_7;
                                }
                                if ((var10_10 = ConcurrentHashMap.compareComparables(var1_1, var5_5, var9_9)) != 0) break block13;
                            }
                            var10_10 = TreeBin.tieBreakOrder(var5_5, var9_9);
                        }
                        var7_7 = var10_10 <= 0 ? var8_8.left : var8_8.right;
                        var11_11 = var7_7;
                        if (var7_7 != null) break block17;
                        var3_3.parent = var8_8;
                        if (var10_10 <= 0) {
                            var8_8.left = var3_3;
                        } else {
                            var8_8.right = var3_3;
                        }
                        var2_2 = TreeBin.balanceInsertion(var2_2, var3_3);
lbl55: // 2 sources:
                        var3_3 = var4_4;
                        continue block0;
                    }
                    var7_7 = var1_1;
                    var1_1 = var11_11;
                } while (true);
                break;
            } while (true);
        }

        static <K, V> TreeNode<K, V> balanceDeletion(TreeNode<K, V> treeNode, TreeNode<K, V> treeNode2) {
            while (treeNode2 != null && treeNode2 != treeNode) {
                TreeNode treeNode3;
                TreeNode<K, V> treeNode4;
                block27 : {
                    boolean bl;
                    TreeNode treeNode5;
                    block29 : {
                        TreeNode treeNode6;
                        TreeNode treeNode7;
                        TreeNode treeNode8;
                        block28 : {
                            TreeNode treeNode9;
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
                                            treeNode7 = treeNode3 = treeNode5.left;
                                            treeNode6 = null;
                                            treeNode9 = null;
                                            if (treeNode3 != treeNode2) break block23;
                                            treeNode6 = treeNode5.right;
                                            treeNode7 = treeNode6;
                                            treeNode3 = treeNode5;
                                            treeNode8 = treeNode7;
                                            treeNode4 = treeNode;
                                            if (treeNode6 != null) {
                                                treeNode3 = treeNode5;
                                                treeNode8 = treeNode7;
                                                treeNode4 = treeNode;
                                                if (treeNode7.red) {
                                                    treeNode7.red = false;
                                                    treeNode5.red = true;
                                                    treeNode4 = TreeBin.rotateLeft(treeNode, treeNode5);
                                                    treeNode = treeNode2.parent;
                                                    treeNode3 = treeNode;
                                                    treeNode = treeNode == null ? null : treeNode3.right;
                                                    treeNode8 = treeNode;
                                                }
                                            }
                                            if (treeNode8 == null) {
                                                treeNode2 = treeNode3;
                                                treeNode = treeNode4;
                                                continue;
                                            }
                                            treeNode7 = treeNode8.left;
                                            treeNode6 = treeNode8.right;
                                            if ((treeNode6 == null || !treeNode6.red) && (treeNode7 == null || !treeNode7.red)) break block24;
                                            if (treeNode6 == null) break block25;
                                            treeNode5 = treeNode3;
                                            treeNode = treeNode8;
                                            treeNode3 = treeNode4;
                                            if (treeNode6.red) break block26;
                                        }
                                        if (treeNode7 != null) {
                                            treeNode7.red = false;
                                        }
                                        treeNode8.red = true;
                                        treeNode3 = TreeBin.rotateRight(treeNode4, treeNode8);
                                        treeNode = treeNode2.parent;
                                        treeNode5 = treeNode;
                                        treeNode = treeNode == null ? treeNode9 : treeNode5.right;
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
                                        treeNode = TreeBin.rotateLeft(treeNode3, treeNode5);
                                    }
                                    treeNode2 = treeNode;
                                    continue;
                                }
                                treeNode8.red = true;
                                treeNode2 = treeNode3;
                                treeNode = treeNode4;
                                continue;
                            }
                            treeNode3 = treeNode5;
                            treeNode8 = treeNode7;
                            treeNode4 = treeNode;
                            if (treeNode7 != null) {
                                treeNode3 = treeNode5;
                                treeNode8 = treeNode7;
                                treeNode4 = treeNode;
                                if (treeNode7.red) {
                                    treeNode7.red = false;
                                    treeNode5.red = true;
                                    treeNode4 = TreeBin.rotateRight(treeNode, treeNode5);
                                    treeNode = treeNode2.parent;
                                    treeNode3 = treeNode;
                                    treeNode = treeNode == null ? null : treeNode3.left;
                                    treeNode8 = treeNode;
                                }
                            }
                            if (treeNode8 == null) {
                                treeNode2 = treeNode3;
                                treeNode = treeNode4;
                                continue;
                            }
                            treeNode9 = treeNode8.left;
                            treeNode7 = treeNode8.right;
                            if ((treeNode9 == null || !treeNode9.red) && (treeNode7 == null || !treeNode7.red)) break block27;
                            if (treeNode9 == null) break block28;
                            treeNode5 = treeNode3;
                            treeNode = treeNode8;
                            treeNode3 = treeNode4;
                            if (treeNode9.red) break block29;
                        }
                        if (treeNode7 != null) {
                            treeNode7.red = false;
                        }
                        treeNode8.red = true;
                        treeNode3 = TreeBin.rotateLeft(treeNode4, treeNode8);
                        treeNode = treeNode2.parent;
                        treeNode5 = treeNode;
                        treeNode = treeNode == null ? treeNode6 : treeNode5.left;
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
                        treeNode = TreeBin.rotateRight(treeNode3, treeNode5);
                    }
                    treeNode2 = treeNode;
                    continue;
                }
                treeNode8.red = true;
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
                TreeNode treeNode5;
                TreeNode treeNode6;
                TreeNode treeNode7 = treeNode3.parent;
                treeNode2 = treeNode7;
                if (treeNode7 == null) {
                    treeNode3.red = false;
                    return treeNode3;
                }
                if (!treeNode2.red) break;
                treeNode7 = treeNode5 = treeNode2.parent;
                if (treeNode5 == null) break;
                treeNode5 = treeNode7.left;
                Object var5_5 = null;
                Object var6_6 = null;
                if (treeNode2 == treeNode5) {
                    treeNode5 = treeNode7.right;
                    if (treeNode5 != null && treeNode5.red) {
                        treeNode5.red = false;
                        treeNode2.red = false;
                        treeNode7.red = true;
                        treeNode3 = treeNode7;
                        continue;
                    }
                    treeNode6 = treeNode2;
                    treeNode4 = treeNode7;
                    treeNode5 = treeNode;
                    treeNode7 = treeNode3;
                    if (treeNode3 == treeNode2.right) {
                        treeNode7 = treeNode2;
                        treeNode5 = TreeBin.rotateLeft(treeNode, treeNode2);
                        treeNode = treeNode7.parent;
                        treeNode2 = treeNode;
                        treeNode = treeNode == null ? var6_6 : treeNode2.parent;
                        treeNode4 = treeNode;
                        treeNode6 = treeNode2;
                    }
                    treeNode = treeNode5;
                    treeNode3 = treeNode7;
                    if (treeNode6 == null) continue;
                    treeNode6.red = false;
                    treeNode = treeNode5;
                    treeNode3 = treeNode7;
                    if (treeNode4 == null) continue;
                    treeNode4.red = true;
                    treeNode = TreeBin.rotateRight(treeNode5, treeNode4);
                    treeNode3 = treeNode7;
                    continue;
                }
                if (treeNode5 != null && treeNode5.red) {
                    treeNode5.red = false;
                    treeNode2.red = false;
                    treeNode7.red = true;
                    treeNode3 = treeNode7;
                    continue;
                }
                treeNode4 = treeNode2;
                treeNode6 = treeNode7;
                treeNode5 = treeNode;
                treeNode7 = treeNode3;
                if (treeNode3 == treeNode2.left) {
                    treeNode7 = treeNode2;
                    treeNode5 = TreeBin.rotateRight(treeNode, treeNode2);
                    treeNode = treeNode7.parent;
                    treeNode2 = treeNode;
                    treeNode = treeNode == null ? var5_5 : treeNode2.parent;
                    treeNode6 = treeNode;
                    treeNode4 = treeNode2;
                }
                treeNode = treeNode5;
                treeNode3 = treeNode7;
                if (treeNode4 == null) continue;
                treeNode4.red = false;
                treeNode = treeNode5;
                treeNode3 = treeNode7;
                if (treeNode6 == null) continue;
                treeNode6.red = true;
                treeNode = TreeBin.rotateLeft(treeNode5, treeNode6);
                treeNode3 = treeNode7;
            } while (true);
            return treeNode;
        }

        static <K, V> boolean checkInvariants(TreeNode<K, V> treeNode) {
            TreeNode treeNode2 = treeNode.parent;
            TreeNode treeNode3 = treeNode.left;
            TreeNode treeNode4 = treeNode.right;
            TreeNode treeNode5 = treeNode.prev;
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
            if (treeNode3 != null && !TreeBin.checkInvariants(treeNode3)) {
                return false;
            }
            return treeNode4 == null || TreeBin.checkInvariants(treeNode4);
        }

        private final void contendedLock() {
            boolean bl = false;
            do {
                int n;
                if (((n = this.lockState) & -3) == 0) {
                    if (!U.compareAndSwapInt(this, LOCKSTATE, n, 1)) continue;
                    if (bl) {
                        this.waiter = null;
                    }
                    return;
                }
                if ((n & 2) == 0) {
                    if (!U.compareAndSwapInt(this, LOCKSTATE, n, n | 2)) continue;
                    bl = true;
                    this.waiter = Thread.currentThread();
                    continue;
                }
                if (!bl) continue;
                LockSupport.park(this);
            } while (true);
        }

        private final void lockRoot() {
            if (!U.compareAndSwapInt(this, LOCKSTATE, 0, 1)) {
                this.contendedLock();
            }
        }

        static <K, V> TreeNode<K, V> rotateLeft(TreeNode<K, V> treeNode, TreeNode<K, V> treeNode2) {
            TreeNode<K, V> treeNode3 = treeNode;
            if (treeNode2 != null) {
                TreeNode treeNode4 = treeNode2.right;
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
                TreeNode treeNode4 = treeNode2.left;
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

        private final void unlockRoot() {
            this.lockState = 0;
        }

        @Override
        final Node<K, V> find(int n, Object treeNode) {
            Object var3_4 = null;
            if (treeNode != null) {
                Object object = this.first;
                while (object != null) {
                    block9 : {
                        int n2 = this.lockState;
                        if ((n2 & 3) != 0) {
                            Object k;
                            if (((Node)object).hash == n && ((k = ((Node)object).key) == treeNode || k != null && ((Object)treeNode).equals(k))) {
                                return object;
                            }
                            object = ((Node)object).next;
                            continue;
                        }
                        if (!U.compareAndSwapInt(this, LOCKSTATE, n2, n2 + 4)) continue;
                        object = this.root;
                        if (object != null) break block9;
                        treeNode = var3_4;
                    }
                    treeNode = ((TreeNode)object).findTreeNode(n, treeNode, null);
                    return treeNode;
                    finally {
                        if (U.getAndAddInt(this, LOCKSTATE, -4) == 6 && (object = this.waiter) != null) {
                            LockSupport.unpark((Thread)object);
                        }
                    }
                }
            }
            return null;
        }

        final TreeNode<K, V> putTreeVal(int n, K object, V v) {
            TreeNode<K, V> treeNode = this.root;
            TreeNode treeNode2 = null;
            int n2 = 0;
            do {
                TreeNode treeNode3;
                block26 : {
                    block16 : {
                        int n3;
                        block18 : {
                            int n4;
                            block22 : {
                                Object object2;
                                block23 : {
                                    block25 : {
                                        TreeNode treeNode4;
                                        block24 : {
                                            block21 : {
                                                block20 : {
                                                    block19 : {
                                                        block17 : {
                                                            block15 : {
                                                                n3 = n;
                                                                if (treeNode != null) break block15;
                                                                object = new TreeNode<K, V>(n, object, v, null, null);
                                                                this.root = object;
                                                                this.first = object;
                                                                break block16;
                                                            }
                                                            n4 = treeNode.hash;
                                                            if (n4 <= n3) break block17;
                                                            n3 = -1;
                                                            break block18;
                                                        }
                                                        if (n4 >= n3) break block19;
                                                        n3 = 1;
                                                        break block18;
                                                    }
                                                    object2 = treeNode.key;
                                                    if (object2 == object || object2 != null && object.equals(object2)) break;
                                                    treeNode3 = treeNode2;
                                                    if (treeNode2 != null) break block20;
                                                    treeNode2 = treeNode4 = ConcurrentHashMap.comparableClassFor(object);
                                                    treeNode3 = treeNode2;
                                                    if (treeNode4 == null) break block21;
                                                    treeNode3 = treeNode2;
                                                }
                                                if ((n4 = ConcurrentHashMap.compareComparables(treeNode3, object, object2)) != 0) break block22;
                                            }
                                            n4 = n2;
                                            if (n2 != 0) break block23;
                                            n2 = 1;
                                            treeNode2 = treeNode.left;
                                            if (treeNode2 == null) break block24;
                                            treeNode2 = treeNode4 = treeNode2.findTreeNode(n3, object, (Class<?>)((Object)treeNode3));
                                            if (treeNode4 != null) break block25;
                                        }
                                        treeNode2 = treeNode.right;
                                        n4 = n2;
                                        if (treeNode2 == null) break block23;
                                        treeNode2 = treeNode4 = treeNode2.findTreeNode(n3, object, (Class<?>)((Object)treeNode3));
                                        n4 = n2;
                                        if (treeNode4 == null) break block23;
                                    }
                                    return treeNode2;
                                }
                                n3 = TreeBin.tieBreakOrder(object, object2);
                                treeNode2 = treeNode3;
                                n2 = n4;
                                break block18;
                            }
                            treeNode2 = treeNode3;
                            n3 = n4;
                        }
                        treeNode3 = n3 <= 0 ? treeNode.left : treeNode.right;
                        if (treeNode3 != null) break block26;
                        treeNode2 = this.first;
                        this.first = object = new TreeNode<Object, V>(n, object, v, treeNode2, treeNode);
                        if (treeNode2 != null) {
                            treeNode2.prev = object;
                        }
                        if (n3 <= 0) {
                            treeNode.left = object;
                        } else {
                            treeNode.right = object;
                        }
                        if (!treeNode.red) {
                            ((TreeNode)object).red = true;
                        } else {
                            this.lockRoot();
                            this.root = TreeBin.balanceInsertion(this.root, object);
                        }
                    }
                    return null;
                    finally {
                        this.unlockRoot();
                    }
                }
                treeNode = treeNode3;
            } while (true);
            return treeNode;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        final boolean removeTreeNode(TreeNode<K, V> treeNode) {
            TreeNode<K, V> treeNode2 = (TreeNode<K, V>)treeNode.next;
            TreeNode treeNode3 = treeNode.prev;
            if (treeNode3 == null) {
                this.first = treeNode2;
            } else {
                treeNode3.next = treeNode2;
            }
            if (treeNode2 != null) {
                treeNode2.prev = treeNode3;
            }
            if (this.first == null) {
                this.root = null;
                return true;
            }
            treeNode2 = this.root;
            treeNode3 = treeNode2;
            if (treeNode2 == null) return true;
            if (treeNode3.right == null) return true;
            treeNode2 = treeNode3.left;
            if (treeNode2 == null) return true;
            if (treeNode2.left == null) {
                return true;
            }
            this.lockRoot();
            TreeNode treeNode4 = treeNode.left;
            TreeNode treeNode5 = treeNode.right;
            if (treeNode4 != null && treeNode5 != null) {
                TreeNode treeNode6;
                treeNode2 = treeNode5;
                while ((treeNode6 = treeNode2.left) != null) {
                    treeNode2 = treeNode6;
                }
                boolean bl = treeNode2.red;
                treeNode2.red = treeNode.red;
                treeNode.red = bl;
                treeNode6 = treeNode2.right;
                TreeNode treeNode7 = treeNode.parent;
                if (treeNode2 == treeNode5) {
                    treeNode.parent = treeNode2;
                    treeNode2.right = treeNode;
                } else {
                    TreeNode treeNode8;
                    treeNode.parent = treeNode8 = treeNode2.parent;
                    if (treeNode8 != null) {
                        if (treeNode2 == treeNode8.left) {
                            treeNode8.left = treeNode;
                        } else {
                            treeNode8.right = treeNode;
                        }
                    }
                    treeNode2.right = treeNode5;
                    treeNode5.parent = treeNode2;
                }
                treeNode.left = null;
                treeNode.right = treeNode6;
                if (treeNode6 != null) {
                    treeNode6.parent = treeNode;
                }
                treeNode2.left = treeNode4;
                treeNode4.parent = treeNode2;
                treeNode2.parent = treeNode7;
                if (treeNode7 == null) {
                    treeNode3 = treeNode2;
                } else if (treeNode == treeNode7.left) {
                    treeNode7.left = treeNode2;
                } else {
                    treeNode7.right = treeNode2;
                }
                treeNode2 = treeNode6 != null ? treeNode6 : treeNode;
            } else {
                treeNode2 = treeNode4 != null ? treeNode4 : (treeNode5 != null ? treeNode5 : treeNode);
            }
            treeNode5 = treeNode3;
            if (treeNode2 != treeNode) {
                treeNode5 = treeNode.parent;
                treeNode2.parent = treeNode5;
                if (treeNode5 == null) {
                    treeNode3 = treeNode2;
                } else if (treeNode == treeNode5.left) {
                    treeNode5.left = treeNode2;
                } else {
                    treeNode5.right = treeNode2;
                }
                treeNode.parent = null;
                treeNode.right = null;
                treeNode.left = null;
                treeNode5 = treeNode3;
            }
            if (!treeNode.red) {
                treeNode5 = TreeBin.balanceDeletion(treeNode5, treeNode2);
            }
            this.root = treeNode5;
            if (treeNode != treeNode2) return false;
            try {
                treeNode2 = treeNode.parent;
                if (treeNode2 == null) return false;
                if (treeNode == treeNode2.left) {
                    treeNode2.left = null;
                } else if (treeNode == treeNode2.right) {
                    treeNode2.right = null;
                }
                treeNode.parent = null;
                return false;
            }
            finally {
                this.unlockRoot();
            }
        }
    }

    static final class TreeNode<K, V>
    extends Node<K, V> {
        TreeNode<K, V> left;
        TreeNode<K, V> parent;
        TreeNode<K, V> prev;
        boolean red;
        TreeNode<K, V> right;

        TreeNode(int n, K k, V v, Node<K, V> node, TreeNode<K, V> treeNode) {
            super(n, k, v, node);
            this.parent = treeNode;
        }

        @Override
        Node<K, V> find(int n, Object object) {
            return this.findTreeNode(n, object, null);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        final TreeNode<K, V> findTreeNode(int var1_1, Object var2_2, Class<?> var3_3) {
            if (var2_2 == null) return null;
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
                                if (var9_9 != null && var2_2.equals(var9_9)) {
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
                        var5_5 = var8_8 = (var4_4 = ConcurrentHashMap.comparableClassFor(var2_2));
                        if (var4_4 == null) ** GOTO lbl-1000
                    }
                    var7_7 = ConcurrentHashMap.compareComparables(var8_8, var2_2, var9_9);
                    var5_5 = var8_8;
                    if (var7_7 != 0) {
                        if (var7_7 >= 0) {
                            var3_3 = var6_6;
                        }
                    } else lbl-1000: // 2 sources:
                    {
                        if ((var8_8 = var6_6.findTreeNode(var1_1, var2_2, (Class<?>)var5_5)) != null) {
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
    }

    static final class ValueIterator<K, V>
    extends BaseIterator<K, V>
    implements Iterator<V>,
    Enumeration<V> {
        ValueIterator(Node<K, V>[] arrnode, int n, int n2, int n3, ConcurrentHashMap<K, V> concurrentHashMap) {
            super(arrnode, n, n2, n3, concurrentHashMap);
        }

        @Override
        public final V next() {
            Node node = this.next;
            if (node != null) {
                Object v = node.val;
                this.lastReturned = node;
                this.advance();
                return v;
            }
            throw new NoSuchElementException();
        }

        @Override
        public final V nextElement() {
            return this.next();
        }
    }

    static final class ValueSpliterator<K, V>
    extends Traverser<K, V>
    implements Spliterator<V> {
        long est;

        ValueSpliterator(Node<K, V>[] arrnode, int n, int n2, int n3, long l) {
            super(arrnode, n, n2, n3);
            this.est = l;
        }

        @Override
        public int characteristics() {
            return 4352;
        }

        @Override
        public long estimateSize() {
            return this.est;
        }

        @Override
        public void forEachRemaining(Consumer<? super V> consumer) {
            if (consumer != null) {
                Node node;
                while ((node = this.advance()) != null) {
                    consumer.accept(node.val);
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super V> consumer) {
            if (consumer != null) {
                Node node = this.advance();
                if (node == null) {
                    return false;
                }
                consumer.accept(node.val);
                return true;
            }
            throw new NullPointerException();
        }

        public ValueSpliterator<K, V> trySplit() {
            Object object;
            int n = this.baseIndex;
            int n2 = this.baseLimit;
            int n3 = n + n2 >>> 1;
            if (n3 <= n) {
                object = null;
            } else {
                long l;
                object = this.tab;
                n = this.baseSize;
                this.baseLimit = n3;
                this.est = l = this.est >>> 1;
                object = new ValueSpliterator<K, V>((Node<K, V>[])object, n, n3, n2, l);
            }
            return object;
        }
    }

    static final class ValuesView<K, V>
    extends CollectionView<K, V, V>
    implements Collection<V>,
    Serializable {
        private static final long serialVersionUID = 2249069246763182397L;

        ValuesView(ConcurrentHashMap<K, V> concurrentHashMap) {
            super(concurrentHashMap);
        }

        @Override
        public final boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final boolean contains(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public void forEach(Consumer<? super V> consumer) {
            if (consumer != null) {
                Object object = this.map.table;
                if (object != null) {
                    Node node;
                    object = new Traverser((Node<K, V>[])object, ((Node<K, V>[])object).length, 0, ((Node<K, V>[])object).length);
                    while ((node = ((Traverser)object).advance()) != null) {
                        consumer.accept(node.val);
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final Iterator<V> iterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            Node<K, V>[] arrnode = concurrentHashMap.table;
            int n = arrnode == null ? 0 : arrnode.length;
            return new ValueIterator(arrnode, n, 0, n, concurrentHashMap);
        }

        @Override
        public final boolean remove(Object object) {
            if (object != null) {
                Iterator<V> iterator = this.iterator();
                while (iterator.hasNext()) {
                    if (!object.equals(iterator.next())) continue;
                    iterator.remove();
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean removeIf(Predicate<? super V> predicate) {
            return this.map.removeValueIf(predicate);
        }

        @Override
        public Spliterator<V> spliterator() {
            Node<K, V>[] arrnode = this.map;
            long l = arrnode.sumCount();
            arrnode = arrnode.table;
            int n = arrnode == null ? 0 : arrnode.length;
            if (l < 0L) {
                l = 0L;
            }
            return new ValueSpliterator(arrnode, n, 0, n, l);
        }
    }

}

