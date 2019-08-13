/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Optional<T> {
    private static final Optional<?> EMPTY = new Optional<T>();
    private final T value;

    private Optional() {
        this.value = null;
    }

    private Optional(T t) {
        this.value = Objects.requireNonNull(t);
    }

    public static <T> Optional<T> empty() {
        return EMPTY;
    }

    public static <T> Optional<T> of(T t) {
        return new Optional<T>(t);
    }

    public static <T> Optional<T> ofNullable(T object) {
        object = object == null ? Optional.empty() : Optional.of(object);
        return object;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Optional)) {
            return false;
        }
        object = (Optional)object;
        return Objects.equals(this.value, ((Optional)object).value);
    }

    public Optional<T> filter(Predicate<? super T> optional) {
        Objects.requireNonNull(optional);
        if (!this.isPresent()) {
            return this;
        }
        optional = optional.test(this.value) ? this : Optional.empty();
        return optional;
    }

    public <U> Optional<U> flatMap(Function<? super T, Optional<U>> function) {
        Objects.requireNonNull(function);
        if (!this.isPresent()) {
            return Optional.empty();
        }
        return Objects.requireNonNull(function.apply(this.value));
    }

    public T get() {
        T t = this.value;
        if (t != null) {
            return t;
        }
        throw new NoSuchElementException("No value present");
    }

    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    public void ifPresent(Consumer<? super T> consumer) {
        T t = this.value;
        if (t != null) {
            consumer.accept(t);
        }
    }

    public boolean isPresent() {
        boolean bl = this.value != null;
        return bl;
    }

    public <U> Optional<U> map(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function);
        if (!this.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(function.apply(this.value));
    }

    public T orElse(T t) {
        block0 : {
            T t2 = this.value;
            if (t2 == null) break block0;
            t = t2;
        }
        return t;
    }

    public T orElseGet(Supplier<? extends T> supplier) {
        T t = this.value;
        supplier = t != null ? t : supplier.get();
        return (T)supplier;
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> supplier) throws Throwable {
        T t = this.value;
        if (t != null) {
            return t;
        }
        throw (Throwable)supplier.get();
    }

    public String toString() {
        Object object = this.value;
        object = object != null ? String.format("Optional[%s]", object) : "Optional.empty";
        return object;
    }
}

