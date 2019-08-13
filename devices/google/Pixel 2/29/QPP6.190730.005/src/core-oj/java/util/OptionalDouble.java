/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.NoSuchElementException;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public final class OptionalDouble {
    private static final OptionalDouble EMPTY = new OptionalDouble();
    private final boolean isPresent;
    private final double value;

    private OptionalDouble() {
        this.isPresent = false;
        this.value = Double.NaN;
    }

    private OptionalDouble(double d) {
        this.isPresent = true;
        this.value = d;
    }

    public static OptionalDouble empty() {
        return EMPTY;
    }

    public static OptionalDouble of(double d) {
        return new OptionalDouble(d);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof OptionalDouble)) {
            return false;
        }
        object = (OptionalDouble)object;
        if (this.isPresent && ((OptionalDouble)object).isPresent) {
            if (Double.compare(this.value, ((OptionalDouble)object).value) != 0) {
                bl = false;
            }
        } else if (this.isPresent != ((OptionalDouble)object).isPresent) {
            bl = false;
        }
        return bl;
    }

    public double getAsDouble() {
        if (this.isPresent) {
            return this.value;
        }
        throw new NoSuchElementException("No value present");
    }

    public int hashCode() {
        int n = this.isPresent ? Double.hashCode(this.value) : 0;
        return n;
    }

    public void ifPresent(DoubleConsumer doubleConsumer) {
        if (this.isPresent) {
            doubleConsumer.accept(this.value);
        }
    }

    public boolean isPresent() {
        return this.isPresent;
    }

    public double orElse(double d) {
        block0 : {
            if (!this.isPresent) break block0;
            d = this.value;
        }
        return d;
    }

    public double orElseGet(DoubleSupplier doubleSupplier) {
        double d = this.isPresent ? this.value : doubleSupplier.getAsDouble();
        return d;
    }

    public <X extends Throwable> double orElseThrow(Supplier<X> supplier) throws Throwable {
        if (this.isPresent) {
            return this.value;
        }
        throw (Throwable)supplier.get();
    }

    public String toString() {
        String string = this.isPresent ? String.format("OptionalDouble[%s]", this.value) : "OptionalDouble.empty";
        return string;
    }
}

