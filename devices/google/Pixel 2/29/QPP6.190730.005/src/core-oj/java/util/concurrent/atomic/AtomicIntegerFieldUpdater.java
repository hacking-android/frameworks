/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.reflect.misc.ReflectUtil;

public abstract class AtomicIntegerFieldUpdater<T> {
    protected AtomicIntegerFieldUpdater() {
    }

    @CallerSensitive
    public static <U> AtomicIntegerFieldUpdater<U> newUpdater(Class<U> class_, String string) {
        return new AtomicIntegerFieldUpdaterImpl<U>(class_, string, Reflection.getCallerClass());
    }

    public final int accumulateAndGet(T t, int n, IntBinaryOperator intBinaryOperator) {
        int n2;
        int n3;
        while (!this.compareAndSet(t, n3 = this.get(t), n2 = intBinaryOperator.applyAsInt(n3, n))) {
        }
        return n2;
    }

    public int addAndGet(T t, int n) {
        int n2;
        int n3;
        while (!this.compareAndSet(t, n3 = this.get(t), n2 = n3 + n)) {
        }
        return n2;
    }

    public abstract boolean compareAndSet(T var1, int var2, int var3);

    public int decrementAndGet(T t) {
        int n;
        int n2;
        while (!this.compareAndSet(t, n2 = this.get(t), n = n2 - 1)) {
        }
        return n;
    }

    public abstract int get(T var1);

    public final int getAndAccumulate(T t, int n, IntBinaryOperator intBinaryOperator) {
        int n2;
        while (!this.compareAndSet(t, n2 = this.get(t), intBinaryOperator.applyAsInt(n2, n))) {
        }
        return n2;
    }

    public int getAndAdd(T t, int n) {
        int n2;
        while (!this.compareAndSet(t, n2 = this.get(t), n2 + n)) {
        }
        return n2;
    }

    public int getAndDecrement(T t) {
        int n;
        while (!this.compareAndSet(t, n = this.get(t), n - 1)) {
        }
        return n;
    }

    public int getAndIncrement(T t) {
        int n;
        while (!this.compareAndSet(t, n = this.get(t), n + 1)) {
        }
        return n;
    }

    public int getAndSet(T t, int n) {
        int n2;
        while (!this.compareAndSet(t, n2 = this.get(t), n)) {
        }
        return n2;
    }

    public final int getAndUpdate(T t, IntUnaryOperator intUnaryOperator) {
        int n;
        while (!this.compareAndSet(t, n = this.get(t), intUnaryOperator.applyAsInt(n))) {
        }
        return n;
    }

    public int incrementAndGet(T t) {
        int n;
        int n2;
        while (!this.compareAndSet(t, n2 = this.get(t), n = n2 + 1)) {
        }
        return n;
    }

    public abstract void lazySet(T var1, int var2);

    public abstract void set(T var1, int var2);

    public final int updateAndGet(T t, IntUnaryOperator intUnaryOperator) {
        int n;
        int n2;
        while (!this.compareAndSet(t, n2 = this.get(t), n = intUnaryOperator.applyAsInt(n2))) {
        }
        return n;
    }

    public abstract boolean weakCompareAndSet(T var1, int var2, int var3);

    private static final class AtomicIntegerFieldUpdaterImpl<T>
    extends AtomicIntegerFieldUpdater<T> {
        private static final Unsafe U = Unsafe.getUnsafe();
        private final Class<?> cclass;
        private final long offset;
        private final Class<T> tclass;

        AtomicIntegerFieldUpdaterImpl(Class<T> class_, String class_2, Class<?> class_3) {
            block3 : {
                int n;
                Field field;
                try {
                    field = class_.getDeclaredField((String)((Object)class_2));
                    n = field.getModifiers();
                    ReflectUtil.ensureMemberAccess(class_3, class_, null, n);
                    if (field.getType() != Integer.TYPE) break block3;
                }
                catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
                if (Modifier.isVolatile(n)) {
                    class_2 = Modifier.isProtected(n) ? class_3 : class_;
                    this.cclass = class_2;
                    this.tclass = class_;
                    this.offset = U.objectFieldOffset(field);
                    return;
                }
                throw new IllegalArgumentException("Must be volatile type");
            }
            throw new IllegalArgumentException("Must be integer type");
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

        @Override
        public final int addAndGet(T t, int n) {
            return this.getAndAdd(t, n) + n;
        }

        @Override
        public final boolean compareAndSet(T t, int n, int n2) {
            this.accessCheck(t);
            return U.compareAndSwapInt(t, this.offset, n, n2);
        }

        @Override
        public final int decrementAndGet(T t) {
            return this.getAndAdd(t, -1) - 1;
        }

        @Override
        public final int get(T t) {
            this.accessCheck(t);
            return U.getIntVolatile(t, this.offset);
        }

        @Override
        public final int getAndAdd(T t, int n) {
            this.accessCheck(t);
            return U.getAndAddInt(t, this.offset, n);
        }

        @Override
        public final int getAndDecrement(T t) {
            return this.getAndAdd(t, -1);
        }

        @Override
        public final int getAndIncrement(T t) {
            return this.getAndAdd(t, 1);
        }

        @Override
        public final int getAndSet(T t, int n) {
            this.accessCheck(t);
            return U.getAndSetInt(t, this.offset, n);
        }

        @Override
        public final int incrementAndGet(T t) {
            return this.getAndAdd(t, 1) + 1;
        }

        @Override
        public final void lazySet(T t, int n) {
            this.accessCheck(t);
            U.putOrderedInt(t, this.offset, n);
        }

        @Override
        public final void set(T t, int n) {
            this.accessCheck(t);
            U.putIntVolatile(t, this.offset, n);
        }

        @Override
        public final boolean weakCompareAndSet(T t, int n, int n2) {
            this.accessCheck(t);
            return U.compareAndSwapInt(t, this.offset, n, n2);
        }
    }

}

