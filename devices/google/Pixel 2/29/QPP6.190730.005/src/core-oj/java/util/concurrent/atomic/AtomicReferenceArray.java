/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import sun.misc.Unsafe;

public class AtomicReferenceArray<E>
implements Serializable {
    private static final int ABASE;
    private static final long ARRAY;
    private static final int ASHIFT;
    private static final Unsafe U;
    private static final long serialVersionUID = -6209656149925076980L;
    private final Object[] array;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        U = Unsafe.getUnsafe();
        try {
            ARRAY = U.objectFieldOffset(AtomicReferenceArray.class.getDeclaredField("array"));
            ABASE = U.arrayBaseOffset(Object[].class);
            int n = U.arrayIndexScale(Object[].class);
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

    public AtomicReferenceArray(int n) {
        this.array = new Object[n];
    }

    public AtomicReferenceArray(E[] arrE) {
        this.array = Arrays.copyOf(arrE, arrE.length, Object[].class);
    }

    private static long byteOffset(int n) {
        return ((long)n << ASHIFT) + (long)ABASE;
    }

    private long checkedByteOffset(int n) {
        if (n >= 0 && n < this.array.length) {
            return AtomicReferenceArray.byteOffset(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("index ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private boolean compareAndSetRaw(long l, E e, E e2) {
        return U.compareAndSwapObject(this.array, l, e, e2);
    }

    private E getRaw(long l) {
        return (E)U.getObjectVolatile(this.array, l);
    }

    private void readObject(ObjectInputStream arrT) throws IOException, ClassNotFoundException {
        Object object = arrT.readFields().get("array", null);
        if (object != null && object.getClass().isArray()) {
            arrT = object;
            if (object.getClass() != Object[].class) {
                arrT = Arrays.copyOf((Object[])object, Array.getLength(object), Object[].class);
            }
            U.putObjectVolatile(this, ARRAY, arrT);
            return;
        }
        throw new InvalidObjectException("Not array type");
    }

    public final E accumulateAndGet(int n, E e, BinaryOperator<E> binaryOperator) {
        E e2;
        Object r;
        long l = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l, e2 = this.getRaw(l), r = binaryOperator.apply(e2, e))) {
        }
        return (E)r;
    }

    public final boolean compareAndSet(int n, E e, E e2) {
        return this.compareAndSetRaw(this.checkedByteOffset(n), e, e2);
    }

    public final E get(int n) {
        return this.getRaw(this.checkedByteOffset(n));
    }

    public final E getAndAccumulate(int n, E e, BinaryOperator<E> binaryOperator) {
        E e2;
        long l = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l, e2 = this.getRaw(l), binaryOperator.apply(e2, e))) {
        }
        return e2;
    }

    public final E getAndSet(int n, E e) {
        return (E)U.getAndSetObject(this.array, this.checkedByteOffset(n), e);
    }

    public final E getAndUpdate(int n, UnaryOperator<E> unaryOperator) {
        E e;
        long l = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l, e = this.getRaw(l), unaryOperator.apply(e))) {
        }
        return e;
    }

    public final void lazySet(int n, E e) {
        U.putOrderedObject(this.array, this.checkedByteOffset(n), e);
    }

    public final int length() {
        return this.array.length;
    }

    public final void set(int n, E e) {
        U.putObjectVolatile(this.array, this.checkedByteOffset(n), e);
    }

    public String toString() {
        int n = this.array.length - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(this.getRaw(AtomicReferenceArray.byteOffset(n2)));
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(',');
            stringBuilder.append(' ');
            ++n2;
        } while (true);
    }

    public final E updateAndGet(int n, UnaryOperator<E> unaryOperator) {
        Object r;
        E e;
        long l = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l, e = this.getRaw(l), r = unaryOperator.apply(e))) {
        }
        return (E)r;
    }

    public final boolean weakCompareAndSet(int n, E e, E e2) {
        return this.compareAndSet(n, e, e2);
    }
}

