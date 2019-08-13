/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.BasicLruCache
 */
package java.lang;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import libcore.util.BasicLruCache;

public abstract class Enum<E extends Enum<E>>
implements Comparable<E>,
Serializable {
    private static final BasicLruCache<Class<? extends Enum>, Object[]> sharedConstantsCache = new BasicLruCache<Class<? extends Enum>, Object[]>(64){

        protected Object[] create(Class<? extends Enum> class_) {
            return Enum.enumValues(class_);
        }
    };
    private final String name;
    private final int ordinal;

    protected Enum(String string, int n) {
        this.name = string;
        this.ordinal = n;
    }

    private static Object[] enumValues(Class<? extends Enum> arrobject) {
        if (!arrobject.isEnum()) {
            return null;
        }
        try {
            arrobject = (Object[])arrobject.getDeclaredMethod("values", new Class[0]).invoke(null, new Object[0]);
            return arrobject;
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    public static <T extends Enum<T>> T[] getSharedConstants(Class<T> class_) {
        return (Enum[])sharedConstantsCache.get(class_);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        throw new InvalidObjectException("can't deserialize enum");
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("can't deserialize enum");
    }

    public static <T extends Enum<T>> T valueOf(Class<T> class_, String charSequence) {
        Objects.requireNonNull(class_, "enumType == null");
        Objects.requireNonNull(class_, "name == null");
        Object object = Enum.getSharedConstants(class_);
        if (object != null) {
            for (int i = ((Enum[])object).length - 1; i >= 0; --i) {
                Enum enum_ = object[i];
                if (!((String)charSequence).equals(enum_.name())) continue;
                return (T)enum_;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("No enum constant ");
            ((StringBuilder)object).append(class_.getCanonicalName());
            ((StringBuilder)object).append(".");
            ((StringBuilder)object).append((String)charSequence);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(class_.toString());
        ((StringBuilder)charSequence).append(" is not an enum type.");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    protected final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public final int compareTo(E e) {
        if (this.getClass() != e.getClass() && this.getDeclaringClass() != ((Enum)e).getDeclaringClass()) {
            throw new ClassCastException();
        }
        return this.ordinal - ((Enum)e).ordinal;
    }

    public final boolean equals(Object object) {
        boolean bl = this == object;
        return bl;
    }

    protected final void finalize() {
    }

    public final Class<E> getDeclaringClass() {
        Class<?> class_;
        block0 : {
            Class<?> class_2 = this.getClass();
            class_ = class_2.getSuperclass();
            if (class_ != Enum.class) break block0;
            class_ = class_2;
        }
        return class_;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final String name() {
        return this.name;
    }

    public final int ordinal() {
        return this.ordinal;
    }

    public String toString() {
        return this.name;
    }

}

