/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.NoSuchElementException;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

public final class OptionalLong {
    private static final OptionalLong EMPTY = new OptionalLong();
    private final boolean isPresent;
    private final long value;

    private OptionalLong() {
        this.isPresent = false;
        this.value = 0L;
    }

    private OptionalLong(long l) {
        this.isPresent = true;
        this.value = l;
    }

    public static OptionalLong empty() {
        return EMPTY;
    }

    public static OptionalLong of(long l) {
        return new OptionalLong(l);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof OptionalLong)) {
            return false;
        }
        object = (OptionalLong)object;
        if (this.isPresent && ((OptionalLong)object).isPresent) {
            if (this.value != ((OptionalLong)object).value) {
                bl = false;
            }
        } else if (this.isPresent != ((OptionalLong)object).isPresent) {
            bl = false;
        }
        return bl;
    }

    public long getAsLong() {
        if (this.isPresent) {
            return this.value;
        }
        throw new NoSuchElementException("No value present");
    }

    public int hashCode() {
        int n = this.isPresent ? Long.hashCode(this.value) : 0;
        return n;
    }

    public void ifPresent(LongConsumer longConsumer) {
        if (this.isPresent) {
            longConsumer.accept(this.value);
        }
    }

    public boolean isPresent() {
        return this.isPresent;
    }

    public long orElse(long l) {
        block0 : {
            if (!this.isPresent) break block0;
            l = this.value;
        }
        return l;
    }

    public long orElseGet(LongSupplier longSupplier) {
        long l = this.isPresent ? this.value : longSupplier.getAsLong();
        return l;
    }

    public <X extends Throwable> long orElseThrow(Supplier<X> supplier) throws Throwable {
        if (this.isPresent) {
            return this.value;
        }
        throw (Throwable)supplier.get();
    }

    public String toString() {
        String string = this.isPresent ? String.format("OptionalLong[%s]", this.value) : "OptionalLong.empty";
        return string;
    }
}

