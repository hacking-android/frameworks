/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.ReflectiveProperty;

public abstract class Property<T, V> {
    private final String mName;
    private final Class<V> mType;

    public Property(Class<V> class_, String string2) {
        this.mName = string2;
        this.mType = class_;
    }

    public static <T, V> Property<T, V> of(Class<T> class_, Class<V> class_2, String string2) {
        return new ReflectiveProperty<T, V>(class_, class_2, string2);
    }

    public abstract V get(T var1);

    public String getName() {
        return this.mName;
    }

    public Class<V> getType() {
        return this.mType;
    }

    public boolean isReadOnly() {
        return false;
    }

    public void set(T object, V v) {
        object = new StringBuilder();
        ((StringBuilder)object).append("Property ");
        ((StringBuilder)object).append(this.getName());
        ((StringBuilder)object).append(" is read-only");
        throw new UnsupportedOperationException(((StringBuilder)object).toString());
    }
}

