/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

public abstract class TypeConverter<T, V> {
    private Class<T> mFromClass;
    private Class<V> mToClass;

    public TypeConverter(Class<T> class_, Class<V> class_2) {
        this.mFromClass = class_;
        this.mToClass = class_2;
    }

    public abstract V convert(T var1);

    Class<T> getSourceType() {
        return this.mFromClass;
    }

    Class<V> getTargetType() {
        return this.mToClass;
    }
}

