/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;
import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.reflect.misc.ReflectUtil;

public abstract class AtomicLongFieldUpdater<T> {
    protected AtomicLongFieldUpdater() {
    }

    @CallerSensitive
    public static <U> AtomicLongFieldUpdater<U> newUpdater(Class<U> class_, String string) {
        Class<?> class_2 = Reflection.getCallerClass();
        if (AtomicLong.VM_SUPPORTS_LONG_CAS) {
            return new CASUpdater<U>(class_, string, class_2);
        }
        return new LockedUpdater<U>(class_, string, class_2);
    }

    public final long accumulateAndGet(T t, long l, LongBinaryOperator longBinaryOperator) {
        long l2;
        long l3;
        while (!this.compareAndSet(t, l2 = this.get(t), l3 = longBinaryOperator.applyAsLong(l2, l))) {
        }
        return l3;
    }

    public long addAndGet(T t, long l) {
        long l2;
        long l3;
        while (!this.compareAndSet(t, l3 = this.get(t), l2 = l3 + l)) {
        }
        return l2;
    }

    public abstract boolean compareAndSet(T var1, long var2, long var4);

    public long decrementAndGet(T t) {
        long l;
        long l2;
        while (!this.compareAndSet(t, l = this.get(t), l2 = l - 1L)) {
        }
        return l2;
    }

    public abstract long get(T var1);

    public final long getAndAccumulate(T t, long l, LongBinaryOperator longBinaryOperator) {
        long l2;
        while (!this.compareAndSet(t, l2 = this.get(t), longBinaryOperator.applyAsLong(l2, l))) {
        }
        return l2;
    }

    public long getAndAdd(T t, long l) {
        long l2;
        while (!this.compareAndSet(t, l2 = this.get(t), l2 + l)) {
        }
        return l2;
    }

    public long getAndDecrement(T t) {
        long l;
        while (!this.compareAndSet(t, l = this.get(t), l - 1L)) {
        }
        return l;
    }

    public long getAndIncrement(T t) {
        long l;
        while (!this.compareAndSet(t, l = this.get(t), l + 1L)) {
        }
        return l;
    }

    public long getAndSet(T t, long l) {
        long l2;
        while (!this.compareAndSet(t, l2 = this.get(t), l)) {
        }
        return l2;
    }

    public final long getAndUpdate(T t, LongUnaryOperator longUnaryOperator) {
        long l;
        while (!this.compareAndSet(t, l = this.get(t), longUnaryOperator.applyAsLong(l))) {
        }
        return l;
    }

    public long incrementAndGet(T t) {
        long l;
        long l2;
        while (!this.compareAndSet(t, l = this.get(t), l2 = l + 1L)) {
        }
        return l2;
    }

    public abstract void lazySet(T var1, long var2);

    public abstract void set(T var1, long var2);

    public final long updateAndGet(T t, LongUnaryOperator longUnaryOperator) {
        long l;
        long l2;
        while (!this.compareAndSet(t, l = this.get(t), l2 = longUnaryOperator.applyAsLong(l))) {
        }
        return l2;
    }

    public abstract boolean weakCompareAndSet(T var1, long var2, long var4);

    private static final class CASUpdater<T>
    extends AtomicLongFieldUpdater<T> {
        private static final Unsafe U = Unsafe.getUnsafe();
        private final Class<?> cclass;
        private final long offset;
        private final Class<T> tclass;

        CASUpdater(Class<T> class_, String class_2, Class<?> class_3) {
            block3 : {
                int n;
                Field field;
                try {
                    field = class_.getDeclaredField((String)((Object)class_2));
                    n = field.getModifiers();
                    ReflectUtil.ensureMemberAccess(class_3, class_, null, n);
                    if (field.getType() != Long.TYPE) break block3;
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
            throw new IllegalArgumentException("Must be long type");
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
        public final long addAndGet(T t, long l) {
            return this.getAndAdd(t, l) + l;
        }

        @Override
        public final boolean compareAndSet(T t, long l, long l2) {
            this.accessCheck(t);
            return U.compareAndSwapLong(t, this.offset, l, l2);
        }

        @Override
        public final long decrementAndGet(T t) {
            return this.getAndAdd(t, -1L) - 1L;
        }

        @Override
        public final long get(T t) {
            this.accessCheck(t);
            return U.getLongVolatile(t, this.offset);
        }

        @Override
        public final long getAndAdd(T t, long l) {
            this.accessCheck(t);
            return U.getAndAddLong(t, this.offset, l);
        }

        @Override
        public final long getAndDecrement(T t) {
            return this.getAndAdd(t, -1L);
        }

        @Override
        public final long getAndIncrement(T t) {
            return this.getAndAdd(t, 1L);
        }

        @Override
        public final long getAndSet(T t, long l) {
            this.accessCheck(t);
            return U.getAndSetLong(t, this.offset, l);
        }

        @Override
        public final long incrementAndGet(T t) {
            return this.getAndAdd(t, 1L) + 1L;
        }

        @Override
        public final void lazySet(T t, long l) {
            this.accessCheck(t);
            U.putOrderedLong(t, this.offset, l);
        }

        @Override
        public final void set(T t, long l) {
            this.accessCheck(t);
            U.putLongVolatile(t, this.offset, l);
        }

        @Override
        public final boolean weakCompareAndSet(T t, long l, long l2) {
            this.accessCheck(t);
            return U.compareAndSwapLong(t, this.offset, l, l2);
        }
    }

    private static final class LockedUpdater<T>
    extends AtomicLongFieldUpdater<T> {
        private static final Unsafe U = Unsafe.getUnsafe();
        private final Class<?> cclass;
        private final long offset;
        private final Class<T> tclass;

        LockedUpdater(Class<T> class_, String class_2, Class<?> class_3) {
            block3 : {
                int n;
                Field field;
                try {
                    field = class_.getDeclaredField((String)((Object)class_2));
                    n = field.getModifiers();
                    ReflectUtil.ensureMemberAccess(class_3, class_, null, n);
                    if (field.getType() != Long.TYPE) break block3;
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
            throw new IllegalArgumentException("Must be long type");
        }

        private final void accessCheck(T t) {
            if (this.cclass.isInstance(t)) {
                return;
            }
            throw this.accessCheckException(t);
        }

        private final RuntimeException accessCheckException(T t) {
            if (this.cclass == this.tclass) {
                return new ClassCastException();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Class ");
            stringBuilder.append(this.cclass.getName());
            stringBuilder.append(" can not access a protected member of class ");
            stringBuilder.append(this.tclass.getName());
            stringBuilder.append(" using an instance of ");
            stringBuilder.append(t.getClass().getName());
            return new RuntimeException(new IllegalAccessException(stringBuilder.toString()));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final boolean compareAndSet(T t, long l, long l2) {
            this.accessCheck(t);
            synchronized (this) {
                if (U.getLong(t, this.offset) != l) {
                    return false;
                }
                U.putLong(t, this.offset, l2);
                return true;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final long get(T t) {
            this.accessCheck(t);
            synchronized (this) {
                return U.getLong(t, this.offset);
            }
        }

        @Override
        public final void lazySet(T t, long l) {
            this.set(t, l);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final void set(T t, long l) {
            this.accessCheck(t);
            synchronized (this) {
                U.putLong(t, this.offset, l);
                return;
            }
        }

        @Override
        public final boolean weakCompareAndSet(T t, long l, long l2) {
            return this.compareAndSet(t, l, l2);
        }
    }

}

