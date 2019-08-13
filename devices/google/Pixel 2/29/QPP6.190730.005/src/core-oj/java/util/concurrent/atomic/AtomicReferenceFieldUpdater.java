/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.reflect.misc.ReflectUtil;

public abstract class AtomicReferenceFieldUpdater<T, V> {
    protected AtomicReferenceFieldUpdater() {
    }

    @CallerSensitive
    public static <U, W> AtomicReferenceFieldUpdater<U, W> newUpdater(Class<U> class_, Class<W> class_2, String string) {
        return new AtomicReferenceFieldUpdaterImpl<U, W>(class_, class_2, string, Reflection.getCallerClass());
    }

    public final V accumulateAndGet(T t, V v, BinaryOperator<V> binaryOperator) {
        Object r;
        V v2;
        while (!this.compareAndSet(t, v2 = this.get(t), r = binaryOperator.apply(v2, v))) {
        }
        return (V)r;
    }

    public abstract boolean compareAndSet(T var1, V var2, V var3);

    public abstract V get(T var1);

    public final V getAndAccumulate(T t, V v, BinaryOperator<V> binaryOperator) {
        V v2;
        while (!this.compareAndSet(t, v2 = this.get(t), binaryOperator.apply(v2, v))) {
        }
        return v2;
    }

    public V getAndSet(T t, V v) {
        V v2;
        while (!this.compareAndSet(t, v2 = this.get(t), v)) {
        }
        return v2;
    }

    public final V getAndUpdate(T t, UnaryOperator<V> unaryOperator) {
        V v;
        while (!this.compareAndSet(t, v = this.get(t), unaryOperator.apply(v))) {
        }
        return v;
    }

    public abstract void lazySet(T var1, V var2);

    public abstract void set(T var1, V var2);

    public final V updateAndGet(T t, UnaryOperator<V> unaryOperator) {
        V v;
        R r;
        while (!this.compareAndSet(t, v = this.get(t), r = unaryOperator.apply(v))) {
        }
        return (V)r;
    }

    public abstract boolean weakCompareAndSet(T var1, V var2, V var3);

    private static final class AtomicReferenceFieldUpdaterImpl<T, V>
    extends AtomicReferenceFieldUpdater<T, V> {
        private static final Unsafe U = Unsafe.getUnsafe();
        private final Class<?> cclass;
        private final long offset;
        private final Class<T> tclass;
        private final Class<V> vclass;

        AtomicReferenceFieldUpdaterImpl(Class<T> class_, Class<V> class_2, String class_3, Class<?> class_4) {
            block3 : {
                block4 : {
                    int n;
                    Field field;
                    try {
                        field = class_.getDeclaredField((String)((Object)class_3));
                        n = field.getModifiers();
                        ReflectUtil.ensureMemberAccess(class_4, class_, null, n);
                        class_3 = field.getType();
                        if (class_2 != class_3) break block3;
                        if (class_2.isPrimitive()) break block4;
                    }
                    catch (Exception exception) {
                        throw new RuntimeException(exception);
                    }
                    if (Modifier.isVolatile(n)) {
                        class_3 = Modifier.isProtected(n) ? class_4 : class_;
                        this.cclass = class_3;
                        this.tclass = class_;
                        this.vclass = class_2;
                        this.offset = U.objectFieldOffset(field);
                        return;
                    }
                    throw new IllegalArgumentException("Must be volatile type");
                }
                throw new IllegalArgumentException("Must be reference type");
            }
            throw new ClassCastException();
        }

        private final void accessCheck(T t) {
            if (!this.cclass.isInstance(t)) {
                this.throwAccessCheckException(t);
            }
        }

        private final void throwAccessCheckException(T t) {
            if (this.cclass == this.tclass) {
                throw new ClassCastException();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Class ");
            stringBuilder.append(this.cclass.getName());
            stringBuilder.append(" can not access a protected member of class ");
            stringBuilder.append(this.tclass.getName());
            stringBuilder.append(" using an instance of ");
            stringBuilder.append(t.getClass().getName());
            throw new RuntimeException(new IllegalAccessException(stringBuilder.toString()));
        }

        static void throwCCE() {
            throw new ClassCastException();
        }

        private final void valueCheck(V v) {
            if (v != null && !this.vclass.isInstance(v)) {
                AtomicReferenceFieldUpdaterImpl.throwCCE();
            }
        }

        @Override
        public final boolean compareAndSet(T t, V v, V v2) {
            this.accessCheck(t);
            this.valueCheck(v2);
            return U.compareAndSwapObject(t, this.offset, v, v2);
        }

        @Override
        public final V get(T t) {
            this.accessCheck(t);
            return (V)U.getObjectVolatile(t, this.offset);
        }

        @Override
        public final V getAndSet(T t, V v) {
            this.accessCheck(t);
            this.valueCheck(v);
            return (V)U.getAndSetObject(t, this.offset, v);
        }

        @Override
        public final void lazySet(T t, V v) {
            this.accessCheck(t);
            this.valueCheck(v);
            U.putOrderedObject(t, this.offset, v);
        }

        @Override
        public final void set(T t, V v) {
            this.accessCheck(t);
            this.valueCheck(v);
            U.putObjectVolatile(t, this.offset, v);
        }

        @Override
        public final boolean weakCompareAndSet(T t, V v, V v2) {
            this.accessCheck(t);
            this.valueCheck(v2);
            return U.compareAndSwapObject(t, this.offset, v, v2);
        }
    }

}

