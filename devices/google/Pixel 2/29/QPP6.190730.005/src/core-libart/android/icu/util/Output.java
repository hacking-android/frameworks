/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

public class Output<T> {
    public T value;

    public Output() {
    }

    public Output(T t) {
        this.value = t;
    }

    public String toString() {
        Object object = this.value;
        object = object == null ? "null" : object.toString();
        return object;
    }
}

