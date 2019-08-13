/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.io.Serializable;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class AtomicBoolean
implements Serializable {
    private static final Unsafe U = Unsafe.getUnsafe();
    private static final long VALUE;
    private static final long serialVersionUID = 4654671469794556979L;
    private volatile int value;

    static {
        try {
            VALUE = U.objectFieldOffset(AtomicBoolean.class.getDeclaredField("value"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public AtomicBoolean() {
    }

    public AtomicBoolean(boolean bl) {
        this.value = bl ? 1 : 0;
    }

    public final boolean compareAndSet(boolean bl, boolean bl2) {
        Unsafe unsafe = U;
        long l = VALUE;
        return unsafe.compareAndSwapInt(this, l, (int)bl, (int)bl2);
    }

    public final boolean get() {
        boolean bl = this.value != 0;
        return bl;
    }

    public final boolean getAndSet(boolean bl) {
        boolean bl2;
        while (!this.compareAndSet(bl2 = this.get(), bl)) {
        }
        return bl2;
    }

    public final void lazySet(boolean bl) {
        U.putOrderedInt(this, VALUE, (int)bl);
    }

    public final void set(boolean bl) {
        this.value = bl ? 1 : 0;
    }

    public String toString() {
        return Boolean.toString(this.get());
    }

    public boolean weakCompareAndSet(boolean bl, boolean bl2) {
        Unsafe unsafe = U;
        long l = VALUE;
        return unsafe.compareAndSwapInt(this, l, (int)bl, (int)bl2);
    }
}

