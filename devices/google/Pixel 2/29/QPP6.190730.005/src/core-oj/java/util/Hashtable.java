/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Hashtable<K, V>
extends Dictionary<K, V>
implements Map<K, V>,
Cloneable,
Serializable {
    private static final int ENTRIES = 2;
    private static final int KEYS = 0;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    private static final int VALUES = 1;
    private static final long serialVersionUID = 1421746759512286392L;
    private transient int count;
    private volatile transient Set<Map.Entry<K, V>> entrySet;
    private volatile transient Set<K> keySet;
    private float loadFactor;
    private transient int modCount = 0;
    private transient HashtableEntry<?, ?>[] table;
    private int threshold;
    private volatile transient Collection<V> values;

    public Hashtable() {
        this(11, 0.75f);
    }

    public Hashtable(int n) {
        this(n, 0.75f);
    }

    public Hashtable(int n, float f) {
        if (n >= 0) {
            if (!(f <= 0.0f) && !Float.isNaN(f)) {
                int n2 = n;
                if (n == 0) {
                    n2 = 1;
                }
                this.loadFactor = f;
                this.table = new HashtableEntry[n2];
                this.threshold = Math.min(n2, 2147483640);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal Load: ");
            stringBuilder.append(f);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal Capacity: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Hashtable(Map<? extends K, ? extends V> map) {
        this(Math.max(map.size() * 2, 11), 0.75f);
        this.putAll(map);
    }

    static /* synthetic */ int access$210(Hashtable hashtable) {
        int n = hashtable.count;
        hashtable.count = n - 1;
        return n;
    }

    static /* synthetic */ int access$508(Hashtable hashtable) {
        int n = hashtable.modCount;
        hashtable.modCount = n + 1;
        return n;
    }

    private void addEntry(int n, K k, V v, int n2) {
        ++this.modCount;
        HashtableEntry<?, ?>[] arrhashtableEntry = this.table;
        if (this.count >= this.threshold) {
            this.rehash();
            arrhashtableEntry = this.table;
            n = k.hashCode();
            n2 = (Integer.MAX_VALUE & n) % arrhashtableEntry.length;
        }
        arrhashtableEntry[n2] = new HashtableEntry<K, V>(n, k, v, arrhashtableEntry[n2]);
        ++this.count;
    }

    private <T> Enumeration<T> getEnumeration(int n) {
        if (this.count == 0) {
            return Collections.emptyEnumeration();
        }
        return new Enumerator(n, false);
    }

    private <T> Iterator<T> getIterator(int n) {
        if (this.count == 0) {
            return Collections.emptyIterator();
        }
        return new Enumerator(n, true);
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        float f = this.loadFactor;
        if (!(f <= 0.0f) && !Float.isNaN(f)) {
            int n = ((ObjectInputStream)object).readInt();
            int n2 = ((ObjectInputStream)object).readInt();
            if (n2 >= 0) {
                int n3;
                int n4 = Math.max(n, (int)((float)n2 / this.loadFactor) + 1);
                n = n3 = (int)((float)(n2 / 20 + n2) / this.loadFactor) + 3;
                if (n3 > n2) {
                    n = n3;
                    if ((n3 & 1) == 0) {
                        n = n3 - 1;
                    }
                }
                n = Math.min(n, n4);
                this.table = new HashtableEntry[n];
                this.threshold = (int)Math.min((float)n * this.loadFactor, 2.14748365E9f);
                this.count = 0;
                for (n = n2; n > 0; --n) {
                    Object object2 = ((ObjectInputStream)object).readObject();
                    Object object3 = ((ObjectInputStream)object).readObject();
                    this.reconstitutionPut(this.table, object2, object3);
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal # of Elements: ");
            ((StringBuilder)object).append(n2);
            throw new StreamCorruptedException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal Load: ");
        ((StringBuilder)object).append(this.loadFactor);
        throw new StreamCorruptedException(((StringBuilder)object).toString());
    }

    private void reconstitutionPut(HashtableEntry<?, ?>[] arrhashtableEntry, K k, V v) throws StreamCorruptedException {
        if (v != null) {
            int n = k.hashCode();
            int n2 = (Integer.MAX_VALUE & n) % arrhashtableEntry.length;
            HashtableEntry<Object, Object> hashtableEntry = arrhashtableEntry[n2];
            while (hashtableEntry != null) {
                if (hashtableEntry.hash == n && hashtableEntry.key.equals(k)) {
                    throw new StreamCorruptedException();
                }
                hashtableEntry = hashtableEntry.next;
            }
            arrhashtableEntry[n2] = new HashtableEntry<K, V>(n, k, v, arrhashtableEntry[n2]);
            ++this.count;
            return;
        }
        throw new StreamCorruptedException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        HashtableEntry hashtableEntry = null;
        synchronized (this) {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeInt(this.table.length);
            objectOutputStream.writeInt(this.count);
            for (int i = 0; i < this.table.length; ++i) {
                HashtableEntry<Object, Object> hashtableEntry2 = this.table[i];
                while (hashtableEntry2 != null) {
                    HashtableEntry hashtableEntry3 = new HashtableEntry(0, hashtableEntry2.key, hashtableEntry2.value, hashtableEntry);
                    hashtableEntry = hashtableEntry3;
                    hashtableEntry2 = hashtableEntry2.next;
                }
            }
        }
        while (hashtableEntry != null) {
            objectOutputStream.writeObject(hashtableEntry.key);
            objectOutputStream.writeObject(hashtableEntry.value);
            hashtableEntry = hashtableEntry.next;
        }
        return;
    }

    @Override
    public void clear() {
        synchronized (this) {
            HashtableEntry<?, ?>[] arrhashtableEntry = this.table;
            ++this.modCount;
            int n = arrhashtableEntry.length;
            while (--n >= 0) {
                arrhashtableEntry[n] = null;
            }
            this.count = 0;
            return;
        }
    }

    /*
     * Exception decompiling
     */
    public Object clone() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[UNCONDITIONALDOLOOP]], but top level block is 2[TRYBLOCK]
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
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public V compute(K object, BiFunction<? super K, ? super V, ? extends V> object2) {
        synchronized (this) {
            Object r;
            Objects.requireNonNull(r);
            HashtableEntry<?, ?>[] arrhashtableEntry = this.table;
            int n = object.hashCode();
            int n2 = (Integer.MAX_VALUE & n) % arrhashtableEntry.length;
            HashtableEntry<Object, Object> hashtableEntry = arrhashtableEntry[n2];
            HashtableEntry<?, ?> hashtableEntry2 = null;
            while (hashtableEntry != null) {
                if (hashtableEntry.hash == n && Objects.equals(hashtableEntry.key, object)) {
                    if ((object = r.apply((Object)object, hashtableEntry.value)) == null) {
                        ++this.modCount;
                        if (hashtableEntry2 != null) {
                            hashtableEntry2.next = hashtableEntry.next;
                        } else {
                            arrhashtableEntry[n2] = hashtableEntry.next;
                        }
                        --this.count;
                    } else {
                        hashtableEntry.value = object;
                    }
                    return (V)object;
                }
                hashtableEntry2 = hashtableEntry;
                hashtableEntry = hashtableEntry.next;
            }
            if ((r = r.apply(object, null)) != null) {
                this.addEntry(n, object, r, n2);
            }
            return (V)r;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public V computeIfAbsent(K object, Function<? super K, ? extends V> object2) {
        synchronized (this) {
            Object r;
            Objects.requireNonNull(r);
            Object object3 = this.table;
            int n = object.hashCode();
            int n2 = (Integer.MAX_VALUE & n) % ((HashtableEntry<?, ?>[])object3).length;
            object3 = object3[n2];
            while (object3 != null) {
                if (object3.hash == n && object3.key.equals(object)) {
                    object = object3.value;
                    return (V)object;
                }
                object3 = object3.next;
            }
            if ((r = r.apply(object)) != null) {
                this.addEntry(n, object, r, n2);
            }
            return (V)r;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public V computeIfPresent(K object, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        synchronized (this) {
            Objects.requireNonNull(biFunction);
            HashtableEntry<?, ?>[] arrhashtableEntry = this.table;
            int n = object.hashCode();
            int n2 = (Integer.MAX_VALUE & n) % arrhashtableEntry.length;
            HashtableEntry<?, ?> hashtableEntry = arrhashtableEntry[n2];
            Object var7_10 = null;
            void var6_8;
            while (var6_8 != null) {
                if (var6_8.hash == n && var6_8.key.equals(object)) {
                    V v = biFunction.apply(object, var6_8.value);
                    if (v == null) {
                        ++this.modCount;
                        if (var7_10 != null) {
                            var7_10.next = var6_8.next;
                        } else {
                            arrhashtableEntry[n2] = var6_8.next;
                        }
                        --this.count;
                    } else {
                        var6_8.value = v;
                    }
                    return v;
                }
                var7_10 = var6_8;
                HashtableEntry hashtableEntry2 = var6_8.next;
            }
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean contains(Object var1_1) {
        block10 : {
            // MONITORENTER : this
            if (var1_1 == null) ** GOTO lbl5
            break block10;
lbl5: // 1 sources:
            var1_1 = new NullPointerException();
            throw var1_1;
        }
        var2_3 = this.table;
        var3_4 = var2_3.length;
        do {
            var4_5 = var3_4 - 1;
            if (var3_4 <= 0) {
                // MONITOREXIT : this
                return false;
            }
            var5_6 /* !! */  = var2_3[var4_5];
            while (var5_6 /* !! */  != null) {
                block9 : {
                    var6_7 = var5_6 /* !! */ .value.equals(var1_1);
                    if (!var6_7) break block9;
                    // MONITOREXIT : this
                    return true;
                }
                var5_6 /* !! */  = var5_6 /* !! */ .next;
            }
            var3_4 = var4_5;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean containsKey(Object object) {
        synchronized (this) {
            Object object2 = this.table;
            int n = object.hashCode();
            object2 = object2[(Integer.MAX_VALUE & n) % ((HashtableEntry<?, ?>[])object2).length];
            while (object2 != null) {
                boolean bl;
                if (object2.hash == n && (bl = object2.key.equals(object))) {
                    return true;
                }
                object2 = object2.next;
            }
            return false;
        }
    }

    @Override
    public boolean containsValue(Object object) {
        return this.contains(object);
    }

    @Override
    public Enumeration<V> elements() {
        synchronized (this) {
            Enumeration<T> enumeration = this.getEnumeration(1);
            return enumeration;
        }
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.entrySet == null) {
            this.entrySet = Collections.synchronizedSet(new EntrySet(), this);
        }
        return this.entrySet;
    }

    /*
     * Exception decompiling
     */
    @Override
    public boolean equals(Object var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[TRYBLOCK]], but top level block is 10[WHILELOOP]
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

    @Override
    public void forEach(BiConsumer<? super K, ? super V> object) {
        synchronized (this) {
            Objects.requireNonNull(object);
            int n = this.modCount;
            for (HashtableEntry<Object, Object> hashtableEntry : this.table) {
                while (hashtableEntry != null) {
                    object.accept(hashtableEntry.key, hashtableEntry.value);
                    hashtableEntry = hashtableEntry.next;
                    if (n == this.modCount) continue;
                    object = new ConcurrentModificationException();
                    throw object;
                }
            }
            return;
        }
    }

    @Override
    public V get(Object object) {
        synchronized (this) {
            Object object2 = this.table;
            int n = object.hashCode();
            object2 = object2[(Integer.MAX_VALUE & n) % ((HashtableEntry<?, ?>[])object2).length];
            while (object2 != null) {
                block6 : {
                    if (object2.hash != n || !object2.key.equals(object)) break block6;
                    object = object2.value;
                    return (V)object;
                }
                object2 = object2.next;
            }
            return null;
        }
    }

    @Override
    public V getOrDefault(Object object, V v) {
        synchronized (this) {
            block3 : {
                object = this.get(object);
                if (object != null) break block3;
                object = v;
            }
            return (V)object;
        }
    }

    @Override
    public int hashCode() {
        synchronized (this) {
            block7 : {
                int n = 0;
                if (this.count == 0 || this.loadFactor < 0.0f) break block7;
                this.loadFactor = -this.loadFactor;
                for (HashtableEntry<Object, Object> hashtableEntry : this.table) {
                    while (hashtableEntry != null) {
                        n += hashtableEntry.hashCode();
                        hashtableEntry = hashtableEntry.next;
                    }
                }
                this.loadFactor = -this.loadFactor;
                return n;
            }
            return 0;
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this) {
            int n = this.count;
            boolean bl = n == 0;
            return bl;
        }
    }

    @Override
    public Set<K> keySet() {
        if (this.keySet == null) {
            this.keySet = Collections.synchronizedSet(new KeySet(), this);
        }
        return this.keySet;
    }

    @Override
    public Enumeration<K> keys() {
        synchronized (this) {
            Enumeration<T> enumeration = this.getEnumeration(0);
            return enumeration;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public V merge(K object, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        synchronized (this) {
            void var7_9;
            Objects.requireNonNull(biFunction);
            HashtableEntry<?, ?>[] arrhashtableEntry = this.table;
            int n = object.hashCode();
            int n2 = (Integer.MAX_VALUE & n) % arrhashtableEntry.length;
            HashtableEntry<?, ?> hashtableEntry = arrhashtableEntry[n2];
            Object var8_11 = null;
            while (var7_9 != null) {
                if (var7_9.hash == n && var7_9.key.equals(object)) {
                    V v2 = biFunction.apply(var7_9.value, v);
                    if (v2 == null) {
                        ++this.modCount;
                        if (var8_11 != null) {
                            var8_11.next = var7_9.next;
                        } else {
                            arrhashtableEntry[n2] = var7_9.next;
                        }
                        --this.count;
                    } else {
                        var7_9.value = v2;
                    }
                    return v2;
                }
                var8_11 = var7_9;
                HashtableEntry hashtableEntry2 = var7_9.next;
            }
            if (v != null) {
                this.addEntry(n, object, v, n2);
            }
            return v;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public V put(K object, V v) {
        synchronized (this) {
            int n;
            Object object2;
            int n2;
            block7 : {
                Throwable throwable2;
                block8 : {
                    if (v != null) {
                        try {
                            object2 = this.table;
                            n2 = object.hashCode();
                            n = (Integer.MAX_VALUE & n2) % ((HashtableEntry<?, ?>[])object2).length;
                            object2 = object2[n];
                            break block7;
                        }
                        catch (Throwable throwable2) {
                            break block8;
                        }
                    }
                    object = new Object();
                    throw object;
                }
                throw throwable2;
            }
            do {
                if (object2 == null) {
                    this.addEntry(n2, object, v, n);
                    return null;
                }
                if (object2.hash == n2 && object2.key.equals(object)) {
                    object = object2.value;
                    object2.value = v;
                    return (V)object;
                }
                object2 = object2.next;
            } while (true);
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> object) {
        synchronized (this) {
            for (Map.Entry entry : object.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public V putIfAbsent(K object, V v) {
        synchronized (this) {
            void var2_2;
            Objects.requireNonNull(var2_2);
            Object object2 = this.table;
            int n = object.hashCode();
            int n2 = (Integer.MAX_VALUE & n) % ((HashtableEntry<?, ?>[])object2).length;
            object2 = object2[n2];
            do {
                if (object2 == null) {
                    this.addEntry(n, object, var2_2, n2);
                    return null;
                }
                if (object2.hash == n && object2.key.equals(object)) {
                    object = object2.value;
                    if (object == null) {
                        object2.value = var2_2;
                    }
                    return (V)object;
                }
                object2 = object2.next;
            } while (true);
        }
    }

    protected void rehash() {
        int n;
        int n2 = this.table.length;
        HashtableEntry<?, ?>[] arrhashtableEntry = this.table;
        int n3 = n = (n2 << 1) + 1;
        if (n - 2147483639 > 0) {
            if (n2 == 2147483639) {
                return;
            }
            n3 = 2147483639;
        }
        HashtableEntry[] arrhashtableEntry2 = new HashtableEntry[n3];
        ++this.modCount;
        this.threshold = (int)Math.min((float)n3 * this.loadFactor, 2.14748365E9f);
        this.table = arrhashtableEntry2;
        do {
            HashtableEntry<?, ?> hashtableEntry;
            n = n2 - 1;
            if (n2 <= 0) break;
            HashtableEntry<Object, Object> hashtableEntry2 = arrhashtableEntry[n];
            while ((hashtableEntry = hashtableEntry2) != null) {
                hashtableEntry2 = hashtableEntry.next;
                n2 = (hashtableEntry.hash & Integer.MAX_VALUE) % n3;
                hashtableEntry.next = arrhashtableEntry2[n2];
                arrhashtableEntry2[n2] = hashtableEntry;
            }
            n2 = n;
        } while (true);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public V remove(Object object) {
        synchronized (this) {
            HashtableEntry<?, ?>[] arrhashtableEntry = this.table;
            int n = object.hashCode();
            int n2 = (Integer.MAX_VALUE & n) % arrhashtableEntry.length;
            HashtableEntry<?, ?> hashtableEntry = arrhashtableEntry[n2];
            Object var6_8 = null;
            void var5_6;
            while (var5_6 != null) {
                if (var5_6.hash == n && var5_6.key.equals(object)) {
                    ++this.modCount;
                    if (var6_8 != null) {
                        var6_8.next = var5_6.next;
                    } else {
                        arrhashtableEntry[n2] = var5_6.next;
                    }
                    --this.count;
                    object = var5_6.value;
                    var5_6.value = null;
                    return (V)object;
                }
                var6_8 = var5_6;
                HashtableEntry hashtableEntry2 = var5_6.next;
            }
            return null;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean remove(Object object, Object object2) {
        synchronized (this) {
            void var2_2;
            Objects.requireNonNull(var2_2);
            HashtableEntry<?, ?>[] arrhashtableEntry = this.table;
            int n = object.hashCode();
            int n2 = (Integer.MAX_VALUE & n) % arrhashtableEntry.length;
            HashtableEntry<Object, Object> hashtableEntry = arrhashtableEntry[n2];
            HashtableEntry<?, ?> hashtableEntry2 = null;
            while (hashtableEntry != null) {
                if (hashtableEntry.hash == n && hashtableEntry.key.equals(object) && hashtableEntry.value.equals(var2_2)) {
                    ++this.modCount;
                    if (hashtableEntry2 != null) {
                        hashtableEntry2.next = hashtableEntry.next;
                    } else {
                        arrhashtableEntry[n2] = hashtableEntry.next;
                    }
                    --this.count;
                    hashtableEntry.value = null;
                    return true;
                }
                hashtableEntry2 = hashtableEntry;
                hashtableEntry = hashtableEntry.next;
            }
            return false;
        }
    }

    @Override
    public V replace(K object, V v) {
        synchronized (this) {
            Objects.requireNonNull(v);
            Object object2 = this.table;
            int n = object.hashCode();
            object2 = object2[(Integer.MAX_VALUE & n) % ((HashtableEntry<?, ?>[])object2).length];
            while (object2 != null) {
                block6 : {
                    if (object2.hash != n || !object2.key.equals(object)) break block6;
                    object = object2.value;
                    object2.value = v;
                    return (V)object;
                }
                object2 = object2.next;
            }
            return null;
        }
    }

    @Override
    public boolean replace(K k, V v, V v2) {
        synchronized (this) {
            Objects.requireNonNull(v);
            Objects.requireNonNull(v2);
            Object object = this.table;
            int n = k.hashCode();
            object = object[(Integer.MAX_VALUE & n) % ((HashtableEntry<?, ?>[])object).length];
            while (object != null) {
                if (object.hash == n && object.key.equals(k)) {
                    if (object.value.equals(v)) {
                        object.value = v2;
                        return true;
                    }
                    return false;
                }
                object = object.next;
            }
            return false;
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> object) {
        synchronized (this) {
            Objects.requireNonNull(object);
            int n = this.modCount;
            for (HashtableEntry<Object, Object> hashtableEntry : this.table) {
                while (hashtableEntry != null) {
                    hashtableEntry.value = Objects.requireNonNull(object.apply(hashtableEntry.key, hashtableEntry.value));
                    hashtableEntry = hashtableEntry.next;
                    if (n == this.modCount) continue;
                    object = new ConcurrentModificationException();
                    throw object;
                }
            }
            return;
        }
    }

    @Override
    public int size() {
        synchronized (this) {
            int n = this.count;
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        synchronized (this) {
            int n = this.size() - 1;
            if (n == -1) {
                return "{}";
            }
            StringBuilder stringBuilder = new StringBuilder();
            Iterator<Map.Entry<K, V>> iterator = this.entrySet().iterator();
            stringBuilder.append('{');
            int n2 = 0;
            do {
                Map.Entry<K, V> entry = iterator.next();
                Object object = entry.getKey();
                entry = entry.getValue();
                object = object == this ? "(this Map)" : object.toString();
                stringBuilder.append((String)object);
                stringBuilder.append('=');
                object = entry == this ? "(this Map)" : entry.toString();
                stringBuilder.append((String)object);
                if (n2 == n) {
                    stringBuilder.append('}');
                    return stringBuilder.toString();
                }
                stringBuilder.append(", ");
                ++n2;
            } while (true);
        }
    }

    @Override
    public Collection<V> values() {
        if (this.values == null) {
            this.values = Collections.synchronizedCollection(new ValueCollection(), this);
        }
        return this.values;
    }

    private class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        private EntrySet() {
        }

        @Override
        public boolean add(Map.Entry<K, V> entry) {
            return super.add(entry);
        }

        @Override
        public void clear() {
            Hashtable.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            object = Hashtable.this.table;
            int n = k.hashCode();
            object = object[(Integer.MAX_VALUE & n) % ((HashtableEntry[])object).length];
            while (object != null) {
                if (((HashtableEntry)object).hash == n && ((HashtableEntry)object).equals(entry)) {
                    return true;
                }
                object = ((HashtableEntry)object).next;
            }
            return false;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return Hashtable.this.getIterator(2);
        }

        @Override
        public boolean remove(Object hashtableEntry) {
            if (!(hashtableEntry instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = hashtableEntry;
            hashtableEntry = entry.getKey();
            HashtableEntry[] arrhashtableEntry = Hashtable.this.table;
            int n = ((Object)hashtableEntry).hashCode();
            int n2 = (Integer.MAX_VALUE & n) % arrhashtableEntry.length;
            hashtableEntry = arrhashtableEntry[n2];
            HashtableEntry hashtableEntry2 = null;
            while (hashtableEntry != null) {
                if (hashtableEntry.hash == n && hashtableEntry.equals(entry)) {
                    Hashtable.access$508(Hashtable.this);
                    if (hashtableEntry2 != null) {
                        hashtableEntry2.next = hashtableEntry.next;
                    } else {
                        arrhashtableEntry[n2] = hashtableEntry.next;
                    }
                    Hashtable.access$210(Hashtable.this);
                    hashtableEntry.value = null;
                    return true;
                }
                hashtableEntry2 = hashtableEntry;
                hashtableEntry = hashtableEntry.next;
            }
            return false;
        }

        @Override
        public int size() {
            return Hashtable.this.count;
        }
    }

    private class Enumerator<T>
    implements Enumeration<T>,
    Iterator<T> {
        HashtableEntry<?, ?> entry;
        protected int expectedModCount;
        int index;
        boolean iterator;
        HashtableEntry<?, ?> lastReturned;
        HashtableEntry<?, ?>[] table;
        int type;

        Enumerator(int n, boolean bl) {
            this.table = Hashtable.this.table;
            this.index = this.table.length;
            this.expectedModCount = Hashtable.this.modCount;
            this.type = n;
            this.iterator = bl;
        }

        @Override
        public boolean hasMoreElements() {
            HashtableEntry<?, ?> hashtableEntry = this.entry;
            int n = this.index;
            HashtableEntry<?, ?>[] arrhashtableEntry = this.table;
            while (hashtableEntry == null && n > 0) {
                hashtableEntry = arrhashtableEntry[--n];
            }
            this.entry = hashtableEntry;
            this.index = n;
            boolean bl = hashtableEntry != null;
            return bl;
        }

        @Override
        public boolean hasNext() {
            return this.hasMoreElements();
        }

        @Override
        public T next() {
            if (Hashtable.this.modCount == this.expectedModCount) {
                return this.nextElement();
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public T nextElement() {
            HashtableEntry<Object, Object> hashtableEntry = this.entry;
            int n = this.index;
            HashtableEntry<?, ?>[] arrhashtableEntry = this.table;
            while (hashtableEntry == null && n > 0) {
                hashtableEntry = arrhashtableEntry[--n];
            }
            this.entry = hashtableEntry;
            this.index = n;
            if (hashtableEntry != null) {
                hashtableEntry = this.entry;
                this.lastReturned = hashtableEntry;
                this.entry = hashtableEntry.next;
                n = this.type;
                if (n == 0) {
                    hashtableEntry = hashtableEntry.key;
                } else if (n == 1) {
                    hashtableEntry = hashtableEntry.value;
                }
                return (T)hashtableEntry;
            }
            throw new NoSuchElementException("Hashtable Enumerator");
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void remove() {
            if (!this.iterator) {
                throw new UnsupportedOperationException();
            }
            if (this.lastReturned == null) {
                throw new IllegalStateException("Hashtable Enumerator");
            }
            if (Hashtable.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            Hashtable hashtable = Hashtable.this;
            synchronized (hashtable) {
                HashtableEntry[] arrhashtableEntry = Hashtable.this.table;
                int n = (this.lastReturned.hash & Integer.MAX_VALUE) % arrhashtableEntry.length;
                HashtableEntry hashtableEntry = arrhashtableEntry[n];
                HashtableEntry hashtableEntry2 = null;
                do {
                    if (hashtableEntry == null) {
                        hashtableEntry = new HashtableEntry();
                        throw hashtableEntry;
                    }
                    if (hashtableEntry == this.lastReturned) {
                        Hashtable.access$508(Hashtable.this);
                        ++this.expectedModCount;
                        if (hashtableEntry2 == null) {
                            arrhashtableEntry[n] = hashtableEntry.next;
                        } else {
                            hashtableEntry2.next = hashtableEntry.next;
                        }
                        Hashtable.access$210(Hashtable.this);
                        this.lastReturned = null;
                        return;
                    }
                    hashtableEntry2 = hashtableEntry;
                    hashtableEntry = hashtableEntry.next;
                } while (true);
            }
        }
    }

    private static class HashtableEntry<K, V>
    implements Map.Entry<K, V> {
        final int hash;
        final K key;
        HashtableEntry<K, V> next;
        V value;

        protected HashtableEntry(int n, K k, V v, HashtableEntry<K, V> hashtableEntry) {
            this.hash = n;
            this.key = k;
            this.value = v;
            this.next = hashtableEntry;
        }

        protected Object clone() {
            int n = this.hash;
            K k = this.key;
            V v = this.value;
            HashtableEntry<K, V> hashtableEntry = this.next;
            hashtableEntry = hashtableEntry == null ? null : (HashtableEntry)hashtableEntry.clone();
            return new HashtableEntry<K, V>(n, k, v, hashtableEntry);
        }

        @Override
        public boolean equals(Object object) {
            boolean bl;
            block1 : {
                boolean bl2 = object instanceof Map.Entry;
                bl = false;
                if (!bl2) {
                    return false;
                }
                object = (Map.Entry)object;
                Object object2 = this.key;
                if (!(object2 == null ? object.getKey() == null : object2.equals(object.getKey())) || !((object2 = this.value) == null ? object.getValue() == null : object2.equals(object.getValue()))) break block1;
                bl = true;
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
            return this.hash ^ Objects.hashCode(this.value);
        }

        @Override
        public V setValue(V v) {
            if (v != null) {
                V v2 = this.value;
                this.value = v;
                return v2;
            }
            throw new NullPointerException();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.key.toString());
            stringBuilder.append("=");
            stringBuilder.append(this.value.toString());
            return stringBuilder.toString();
        }
    }

    private class KeySet
    extends AbstractSet<K> {
        private KeySet() {
        }

        @Override
        public void clear() {
            Hashtable.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return Hashtable.this.containsKey(object);
        }

        @Override
        public Iterator<K> iterator() {
            return Hashtable.this.getIterator(0);
        }

        @Override
        public boolean remove(Object object) {
            boolean bl = Hashtable.this.remove(object) != null;
            return bl;
        }

        @Override
        public int size() {
            return Hashtable.this.count;
        }
    }

    private class ValueCollection
    extends AbstractCollection<V> {
        private ValueCollection() {
        }

        @Override
        public void clear() {
            Hashtable.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return Hashtable.this.containsValue(object);
        }

        @Override
        public Iterator<V> iterator() {
            return Hashtable.this.getIterator(1);
        }

        @Override
        public int size() {
            return Hashtable.this.count;
        }
    }

}

