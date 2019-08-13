/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.NoSuchElementException;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public final class OptionalInt {
    private static final OptionalInt EMPTY = new OptionalInt();
    private final boolean isPresent;
    private final int value;

    private OptionalInt() {
        this.isPresent = false;
        this.value = 0;
    }

    private OptionalInt(int n) {
        this.isPresent = true;
        this.value = n;
    }

    public static OptionalInt empty() {
        return EMPTY;
    }

    public static OptionalInt of(int n) {
        return new OptionalInt(n);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof OptionalInt)) {
            return false;
        }
        object = (OptionalInt)object;
        if (this.isPresent && ((OptionalInt)object).isPresent) {
            if (this.value != ((OptionalInt)object).value) {
                bl = false;
            }
        } else if (this.isPresent != ((OptionalInt)object).isPresent) {
            bl = false;
        }
        return bl;
    }

    public int getAsInt() {
        if (this.isPresent) {
            return this.value;
        }
        throw new NoSuchElementException("No value present");
    }

    public int hashCode() {
        int n = this.isPresent ? Integer.hashCode(this.value) : 0;
        return n;
    }

    public void ifPresent(IntConsumer intConsumer) {
        if (this.isPresent) {
            intConsumer.accept(this.value);
        }
    }

    public boolean isPresent() {
        return this.isPresent;
    }

    public int orElse(int n) {
        block0 : {
            if (!this.isPresent) break block0;
            n = this.value;
        }
        return n;
    }

    public int orElseGet(IntSupplier intSupplier) {
        int n = this.isPresent ? this.value : intSupplier.getAsInt();
        return n;
    }

    public <X extends Throwable> int orElseThrow(Supplier<X> supplier) throws Throwable {
        if (this.isPresent) {
            return this.value;
        }
        throw (Throwable)supplier.get();
    }

    public String toString() {
        String string = this.isPresent ? String.format("OptionalInt[%s]", this.value) : "OptionalInt.empty";
        return string;
    }
}

