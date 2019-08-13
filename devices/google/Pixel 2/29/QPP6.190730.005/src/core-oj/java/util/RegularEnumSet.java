/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

class RegularEnumSet<E extends Enum<E>>
extends EnumSet<E> {
    private static final long serialVersionUID = 3411599620347842686L;
    private long elements = 0L;

    RegularEnumSet(Class<E> class_, Enum<?>[] arrenum) {
        super(class_, arrenum);
    }

    static /* synthetic */ long access$074(RegularEnumSet regularEnumSet, long l) {
        regularEnumSet.elements = l = regularEnumSet.elements & l;
        return l;
    }

    @Override
    public boolean add(E e) {
        this.typeCheck(e);
        long l = this.elements;
        this.elements |= 1L << ((Enum)e).ordinal();
        boolean bl = this.elements != l;
        return bl;
    }

    @Override
    void addAll() {
        if (this.universe.length != 0) {
            this.elements = -1L >>> -this.universe.length;
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> regularEnumSet) {
        if (!(regularEnumSet instanceof RegularEnumSet)) {
            return super.addAll(regularEnumSet);
        }
        regularEnumSet = regularEnumSet;
        Class class_ = regularEnumSet.elementType;
        Serializable serializable = this.elementType;
        boolean bl = false;
        if (class_ != serializable) {
            if (regularEnumSet.isEmpty()) {
                return false;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(regularEnumSet.elementType);
            ((StringBuilder)serializable).append(" != ");
            ((StringBuilder)serializable).append(this.elementType);
            throw new ClassCastException(((StringBuilder)serializable).toString());
        }
        long l = this.elements;
        this.elements |= regularEnumSet.elements;
        if (this.elements != l) {
            bl = true;
        }
        return bl;
    }

    @Override
    void addRange(E e, E e2) {
        this.elements = -1L >>> ((Enum)e).ordinal() - ((Enum)e2).ordinal() - 1 << ((Enum)e).ordinal();
    }

    @Override
    public void clear() {
        this.elements = 0L;
    }

    @Override
    void complement() {
        if (this.universe.length != 0) {
            this.elements = this.elements;
            this.elements &= -1L >>> -this.universe.length;
        }
    }

    @Override
    public boolean contains(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        Class<?> class_ = object.getClass();
        if (class_ != this.elementType && class_.getSuperclass() != this.elementType) {
            return false;
        }
        if ((this.elements & 1L << ((Enum)object).ordinal()) != 0L) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean containsAll(Collection<?> regularEnumSet) {
        if (!(regularEnumSet instanceof RegularEnumSet)) {
            return super.containsAll(regularEnumSet);
        }
        regularEnumSet = regularEnumSet;
        if (regularEnumSet.elementType != this.elementType) {
            return regularEnumSet.isEmpty();
        }
        boolean bl = (regularEnumSet.elements & this.elements) == 0L;
        return bl;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RegularEnumSet)) {
            return super.equals(object);
        }
        object = (RegularEnumSet)object;
        Class class_ = ((RegularEnumSet)object).elementType;
        Class class_2 = this.elementType;
        boolean bl = true;
        boolean bl2 = true;
        if (class_ != class_2) {
            if (this.elements != 0L || ((RegularEnumSet)object).elements != 0L) {
                bl2 = false;
            }
            return bl2;
        }
        bl2 = ((RegularEnumSet)object).elements == this.elements ? bl : false;
        return bl2;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.elements == 0L;
        return bl;
    }

    @Override
    public Iterator<E> iterator() {
        return new EnumSetIterator();
    }

    @Override
    public boolean remove(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        Class<?> class_ = object.getClass();
        if (class_ != this.elementType && class_.getSuperclass() != this.elementType) {
            return false;
        }
        long l = this.elements;
        this.elements &= 1L << ((Enum)object).ordinal();
        if (this.elements != l) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean removeAll(Collection<?> object) {
        if (!(object instanceof RegularEnumSet)) {
            return super.removeAll((Collection<?>)object);
        }
        RegularEnumSet regularEnumSet = (RegularEnumSet)object;
        Class class_ = regularEnumSet.elementType;
        object = this.elementType;
        boolean bl = false;
        if (class_ != object) {
            return false;
        }
        long l = this.elements;
        this.elements &= regularEnumSet.elements;
        if (this.elements != l) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean retainAll(Collection<?> regularEnumSet) {
        if (!(regularEnumSet instanceof RegularEnumSet)) {
            return super.retainAll(regularEnumSet);
        }
        regularEnumSet = regularEnumSet;
        Class class_ = regularEnumSet.elementType;
        Class class_2 = this.elementType;
        boolean bl = true;
        boolean bl2 = true;
        if (class_ != class_2) {
            if (this.elements == 0L) {
                bl2 = false;
            }
            this.elements = 0L;
            return bl2;
        }
        long l = this.elements;
        this.elements &= regularEnumSet.elements;
        bl2 = this.elements != l ? bl : false;
        return bl2;
    }

    @Override
    public int size() {
        return Long.bitCount(this.elements);
    }

    private class EnumSetIterator<E extends Enum<E>>
    implements Iterator<E> {
        long lastReturned = 0L;
        long unseen;

        EnumSetIterator() {
            this.unseen = RegularEnumSet.this.elements;
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.unseen != 0L;
            return bl;
        }

        @Override
        public E next() {
            long l = this.unseen;
            if (l != 0L) {
                this.lastReturned = -l & l;
                this.unseen = l - this.lastReturned;
                return (E)RegularEnumSet.this.universe[Long.numberOfTrailingZeros(this.lastReturned)];
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            long l = this.lastReturned;
            if (l != 0L) {
                RegularEnumSet.access$074(RegularEnumSet.this, l);
                this.lastReturned = 0L;
                return;
            }
            throw new IllegalStateException();
        }
    }

}

