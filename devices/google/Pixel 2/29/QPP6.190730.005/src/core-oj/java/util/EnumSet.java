/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.JumboEnumSet;
import java.util.RegularEnumSet;

public abstract class EnumSet<E extends Enum<E>>
extends AbstractSet<E>
implements Cloneable,
Serializable {
    private static Enum<?>[] ZERO_LENGTH_ENUM_ARRAY = new Enum[0];
    final Class<E> elementType;
    final Enum<?>[] universe;

    EnumSet(Class<E> class_, Enum<?>[] arrenum) {
        this.elementType = class_;
        this.universe = arrenum;
    }

    public static <E extends Enum<E>> EnumSet<E> allOf(Class<E> serializable) {
        serializable = EnumSet.noneOf(serializable);
        ((EnumSet)serializable).addAll();
        return serializable;
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(EnumSet<E> enumSet) {
        enumSet = EnumSet.copyOf(enumSet);
        enumSet.complement();
        return enumSet;
    }

    public static <E extends Enum<E>> EnumSet<E> copyOf(Collection<E> collection) {
        if (collection instanceof EnumSet) {
            return ((EnumSet)collection).clone();
        }
        if (!collection.isEmpty()) {
            Iterator<E> iterator = collection.iterator();
            collection = EnumSet.of((Enum)iterator.next());
            while (iterator.hasNext()) {
                ((AbstractCollection)collection).add((Enum)iterator.next());
            }
            return collection;
        }
        throw new IllegalArgumentException("Collection is empty");
    }

    public static <E extends Enum<E>> EnumSet<E> copyOf(EnumSet<E> enumSet) {
        return enumSet.clone();
    }

    private static <E extends Enum<E>> E[] getUniverse(Class<E> class_) {
        return (Enum[])class_.getEnumConstantsShared();
    }

    public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> class_) {
        Object object = EnumSet.getUniverse(class_);
        if (object != null) {
            if (((Enum[])object).length <= 64) {
                return new RegularEnumSet<E>(class_, (Enum<?>[])object);
            }
            return new JumboEnumSet<E>(class_, (Enum<?>[])object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(class_);
        ((StringBuilder)object).append(" not an enum");
        throw new ClassCastException(((StringBuilder)object).toString());
    }

    public static <E extends Enum<E>> EnumSet<E> of(E e) {
        EnumSet<E> enumSet = EnumSet.noneOf(((Enum)e).getDeclaringClass());
        enumSet.add(e);
        return enumSet;
    }

    public static <E extends Enum<E>> EnumSet<E> of(E e, E e2) {
        EnumSet<E> enumSet = EnumSet.noneOf(((Enum)e).getDeclaringClass());
        enumSet.add(e);
        enumSet.add(e2);
        return enumSet;
    }

    public static <E extends Enum<E>> EnumSet<E> of(E e, E e2, E e3) {
        EnumSet<E> enumSet = EnumSet.noneOf(((Enum)e).getDeclaringClass());
        enumSet.add(e);
        enumSet.add(e2);
        enumSet.add(e3);
        return enumSet;
    }

    public static <E extends Enum<E>> EnumSet<E> of(E e, E e2, E e3, E e4) {
        EnumSet<E> enumSet = EnumSet.noneOf(((Enum)e).getDeclaringClass());
        enumSet.add(e);
        enumSet.add(e2);
        enumSet.add(e3);
        enumSet.add(e4);
        return enumSet;
    }

    public static <E extends Enum<E>> EnumSet<E> of(E e, E e2, E e3, E e4, E e5) {
        EnumSet<E> enumSet = EnumSet.noneOf(((Enum)e).getDeclaringClass());
        enumSet.add(e);
        enumSet.add(e2);
        enumSet.add(e3);
        enumSet.add(e4);
        enumSet.add(e5);
        return enumSet;
    }

    @SafeVarargs
    public static <E extends Enum<E>> EnumSet<E> of(E e, E ... arrE) {
        EnumSet<E> enumSet = EnumSet.noneOf(((Enum)e).getDeclaringClass());
        enumSet.add(e);
        int n = arrE.length;
        for (int i = 0; i < n; ++i) {
            enumSet.add(arrE[i]);
        }
        return enumSet;
    }

    public static <E extends Enum<E>> EnumSet<E> range(E e, E e2) {
        if (((Enum)e).compareTo(e2) <= 0) {
            EnumSet<E> enumSet = EnumSet.noneOf(((Enum)e).getDeclaringClass());
            enumSet.addRange(e, e2);
            return enumSet;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(e);
        stringBuilder.append(" > ");
        stringBuilder.append(e2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    abstract void addAll();

    abstract void addRange(E var1, E var2);

    public EnumSet<E> clone() {
        try {
            EnumSet enumSet = (EnumSet)Object.super.clone();
            return enumSet;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    abstract void complement();

    final void typeCheck(E object) {
        Class<?> class_ = object.getClass();
        if (class_ != this.elementType && class_.getSuperclass() != this.elementType) {
            object = new StringBuilder();
            ((StringBuilder)object).append(class_);
            ((StringBuilder)object).append(" != ");
            ((StringBuilder)object).append(this.elementType);
            throw new ClassCastException(((StringBuilder)object).toString());
        }
    }

    Object writeReplace() {
        return new SerializationProxy<E>(this);
    }

    private static class SerializationProxy<E extends Enum<E>>
    implements Serializable {
        private static final long serialVersionUID = 362491234563181265L;
        private final Class<E> elementType;
        private final Enum<?>[] elements;

        SerializationProxy(EnumSet<E> enumSet) {
            this.elementType = enumSet.elementType;
            this.elements = enumSet.toArray(ZERO_LENGTH_ENUM_ARRAY);
        }

        private Object readResolve() {
            EnumSet<Enum<?>> enumSet = EnumSet.noneOf(this.elementType);
            Enum<?>[] arrenum = this.elements;
            int n = arrenum.length;
            for (int i = 0; i < n; ++i) {
                enumSet.add(arrenum[i]);
            }
            return enumSet;
        }
    }

}

