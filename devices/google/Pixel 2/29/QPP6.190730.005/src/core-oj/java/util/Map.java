/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.-$
 *  java.util.-$$Lambda
 *  java.util.-$$Lambda$Map
 *  java.util.-$$Lambda$Map$Entry
 *  java.util.-$$Lambda$Map$Entry$acJOHw6hO1wh4v9r2vtUuCFe5vI
 *  java.util.-$$Lambda$Map$Entry$zJtjVuaqJl6rzQLvCcTd4dnXnnw
 */
package java.util;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.-$;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.Set;
import java.util._$$Lambda$Map$Entry$Y3nKRmSXx8yzU_Ap_vOwqAqvBas;
import java.util._$$Lambda$Map$Entry$acJOHw6hO1wh4v9r2vtUuCFe5vI;
import java.util._$$Lambda$Map$Entry$g8sc1MgjjhwTaK8zHulzMasixMw;
import java.util._$$Lambda$Map$Entry$zJtjVuaqJl6rzQLvCcTd4dnXnnw;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Map<K, V> {
    public void clear();

    default public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        V v = this.get(k);
        biFunction = biFunction.apply(k, v);
        if (biFunction == null) {
            if (v == null && !this.containsKey(k)) {
                return null;
            }
            this.remove(k);
            return null;
        }
        this.put(k, biFunction);
        return (V)biFunction;
    }

    default public V computeIfAbsent(K k, Function<? super K, ? extends V> function) {
        Objects.requireNonNull(function);
        V v = this.get(k);
        if (v == null && (function = function.apply(k)) != null) {
            this.put(k, function);
            return (V)function;
        }
        return v;
    }

    default public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        V v = this.get(k);
        if (v != null) {
            if ((biFunction = biFunction.apply(k, v)) != null) {
                this.put(k, biFunction);
                return (V)biFunction;
            }
            this.remove(k);
            return null;
        }
        return null;
    }

    public boolean containsKey(Object var1);

    public boolean containsValue(Object var1);

    public Set<Entry<K, V>> entrySet();

    public boolean equals(Object var1);

    default public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(biConsumer);
        for (Entry<K, V> entry : this.entrySet()) {
            K k;
            try {
                k = entry.getKey();
                entry = entry.getValue();
            }
            catch (IllegalStateException illegalStateException) {
                throw new ConcurrentModificationException(illegalStateException);
            }
            biConsumer.accept(k, entry);
        }
    }

    public V get(Object var1);

    default public V getOrDefault(Object object, V v) {
        V v2 = this.get(object);
        object = v2 == null && !this.containsKey(object) ? v : v2;
        return (V)object;
    }

    public int hashCode();

    public boolean isEmpty();

    public Set<K> keySet();

    default public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        V v2 = this.get(k);
        if (v2 != null) {
            v = biFunction.apply(v2, v);
        }
        if (v == null) {
            this.remove(k);
        } else {
            this.put(k, v);
        }
        return v;
    }

    public V put(K var1, V var2);

    public void putAll(Map<? extends K, ? extends V> var1);

    default public V putIfAbsent(K k, V v) {
        V v2;
        V v3 = v2 = this.get(k);
        if (v2 == null) {
            v3 = this.put(k, v);
        }
        return v3;
    }

    public V remove(Object var1);

    default public boolean remove(Object object, Object object2) {
        V v = this.get(object);
        if (Objects.equals(v, object2) && (v != null || this.containsKey(object))) {
            this.remove(object);
            return true;
        }
        return false;
    }

    default public V replace(K k, V v) {
        V v2;
        V v3 = v2 = this.get(k);
        if (v2 != null || this.containsKey(k)) {
            v3 = this.put(k, v);
        }
        return v3;
    }

    default public boolean replace(K k, V v, V v2) {
        V v3 = this.get(k);
        if (Objects.equals(v3, v) && (v3 != null || this.containsKey(k))) {
            this.put(k, v2);
            return true;
        }
        return false;
    }

    default public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        for (Entry<K, K> entry : this.entrySet()) {
            Object object;
            V v;
            try {
                object = entry.getKey();
                v = entry.getValue();
            }
            catch (IllegalStateException illegalStateException) {
                throw new ConcurrentModificationException(illegalStateException);
            }
            object = biFunction.apply(object, v);
            try {
                entry.setValue(object);
            }
            catch (IllegalStateException illegalStateException) {
                throw new ConcurrentModificationException(illegalStateException);
            }
        }
    }

    public int size();

    public Collection<V> values();

    public static interface Entry<K, V> {
        public static <K extends Comparable<? super K>, V> Comparator<Entry<K, V>> comparingByKey() {
            return (Comparator)((Object)((Serializable)_$$Lambda$Map$Entry$zJtjVuaqJl6rzQLvCcTd4dnXnnw.INSTANCE));
        }

        public static <K, V> Comparator<Entry<K, V>> comparingByKey(Comparator<? super K> comparator) {
            Objects.requireNonNull(comparator);
            return new _$$Lambda$Map$Entry$g8sc1MgjjhwTaK8zHulzMasixMw(comparator);
        }

        public static <K, V extends Comparable<? super V>> Comparator<Entry<K, V>> comparingByValue() {
            return (Comparator)((Object)((Serializable)_$$Lambda$Map$Entry$acJOHw6hO1wh4v9r2vtUuCFe5vI.INSTANCE));
        }

        public static <K, V> Comparator<Entry<K, V>> comparingByValue(Comparator<? super V> comparator) {
            Objects.requireNonNull(comparator);
            return new _$$Lambda$Map$Entry$Y3nKRmSXx8yzU_Ap_vOwqAqvBas(comparator);
        }

        public static /* synthetic */ int lambda$comparingByKey$6d558cbf$1(Comparator comparator, Entry entry, Entry entry2) {
            return comparator.compare(entry.getKey(), entry2.getKey());
        }

        public static /* synthetic */ int lambda$comparingByKey$bbdbfea9$1(Entry entry, Entry entry2) {
            return ((Comparable)entry.getKey()).compareTo(entry2.getKey());
        }

        public static /* synthetic */ int lambda$comparingByValue$1065357e$1(Entry entry, Entry entry2) {
            return ((Comparable)entry.getValue()).compareTo(entry2.getValue());
        }

        public static /* synthetic */ int lambda$comparingByValue$827a17d5$1(Comparator comparator, Entry entry, Entry entry2) {
            return comparator.compare(entry.getValue(), entry2.getValue());
        }

        public boolean equals(Object var1);

        public K getKey();

        public V getValue();

        public int hashCode();

        public V setValue(V var1);
    }

}

