/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent._$$Lambda$ConcurrentMap$T12JRbgGLhxGbYCuTfff6_dTrMk;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ConcurrentMap<K, V>
extends Map<K, V> {
    public static /* synthetic */ void lambda$replaceAll$0(ConcurrentMap concurrentMap, BiFunction biFunction, Object object, Object object2) {
        while (!concurrentMap.replace(object, object2, biFunction.apply(object, object2))) {
            Object v = concurrentMap.get(object);
            object2 = v;
            if (v != null) continue;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    default public V compute(K var1_1, BiFunction<? super K, ? super V, ? extends V> var2_2) {
        do lbl-1000: // 3 sources:
        {
            var3_3 = this.get(var1_1);
            while ((var4_4 = var2_2.apply(var1_1, var3_3)) != null) {
                block2 : {
                    if (var3_3 == null) break block2;
                    if (!this.replace(var1_1, var3_3, var4_4)) ** GOTO lbl-1000
                    return var4_4;
                }
                var5_5 = this.putIfAbsent(var1_1, var4_4);
                var3_3 = var5_5;
                if (var5_5 != null) continue;
                return var4_4;
            }
            if (var3_3 == null) return null;
        } while (!this.remove(var1_1, var3_3));
        return null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    default public V computeIfAbsent(K object, Function<? super K, ? extends V> function) {
        void var1_5;
        void var2_6;
        Objects.requireNonNull(var2_6);
        Object v = this.get(object);
        if (v == null) {
            Object r;
            Object r2 = r = var2_6.apply(object);
            if (r != null) {
                Object r3 = this.putIfAbsent(object, r2);
                if (r3 != null) return var1_5;
                Object r4 = r2;
                return var1_5;
            }
        }
        Object v2 = v;
        return var1_5;
    }

    @Override
    default public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Object v;
        Objects.requireNonNull(biFunction);
        while ((v = this.get(k)) != null) {
            V v2 = biFunction.apply(k, v);
            if (!(v2 == null ? this.remove(k, v) : this.replace(k, v, v2))) continue;
            return v2;
        }
        return null;
    }

    @Override
    default public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Objects.requireNonNull(biConsumer);
        for (Map.Entry entry : this.entrySet()) {
            Object k;
            try {
                k = entry.getKey();
                entry = entry.getValue();
            }
            catch (IllegalStateException illegalStateException) {
                continue;
            }
            biConsumer.accept(k, entry);
        }
    }

    @Override
    default public V getOrDefault(Object object, V v) {
        if ((object = this.get(object)) == null) {
            object = v;
        }
        return (V)object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    default public V merge(K var1_1, V var2_2, BiFunction<? super V, ? super V, ? extends V> var3_3) {
        Objects.requireNonNull(var3_3);
        Objects.requireNonNull(var2_2);
        block0 : do lbl-1000: // 3 sources:
        {
            var4_4 = this.get(var1_1);
            do {
                block2 : {
                    block3 : {
                        if (var4_4 == null) break block2;
                        var5_5 = var3_3.apply(var4_4, var2_2);
                        if (var5_5 == null) break block3;
                        if (!this.replace(var1_1, var4_4, var5_5)) ** GOTO lbl-1000
                        return var5_5;
                    }
                    if (!this.remove(var1_1, var4_4)) continue block0;
                    return null;
                }
                var5_5 = this.putIfAbsent(var1_1, var2_2);
                var4_4 = var5_5;
            } while (var5_5 != null);
            break;
        } while (true);
        return var2_2;
    }

    @Override
    public V putIfAbsent(K var1, V var2);

    @Override
    public boolean remove(Object var1, Object var2);

    @Override
    public V replace(K var1, V var2);

    @Override
    public boolean replace(K var1, V var2, V var3);

    @Override
    default public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        this.forEach(new _$$Lambda$ConcurrentMap$T12JRbgGLhxGbYCuTfff6_dTrMk(this, biFunction));
    }
}

