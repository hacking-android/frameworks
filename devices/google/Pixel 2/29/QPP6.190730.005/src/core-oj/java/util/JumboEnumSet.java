/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

class JumboEnumSet<E extends Enum<E>>
extends EnumSet<E> {
    private static final long serialVersionUID = 334349849919042784L;
    private long[] elements;
    private int size = 0;

    JumboEnumSet(Class<E> class_, Enum<?>[] arrenum) {
        super(class_, arrenum);
        this.elements = new long[arrenum.length + 63 >>> 6];
    }

    static /* synthetic */ int access$110(JumboEnumSet jumboEnumSet) {
        int n = jumboEnumSet.size;
        jumboEnumSet.size = n - 1;
        return n;
    }

    private boolean recalculateSize() {
        int n = this.size;
        boolean bl = false;
        this.size = 0;
        for (long l : this.elements) {
            this.size += Long.bitCount(l);
        }
        if (this.size != n) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean add(E object) {
        this.typeCheck(object);
        int n = ((Enum)object).ordinal();
        int n2 = n >>> 6;
        object = this.elements;
        E e = object[n2];
        object[n2] = object[n2] | 1L << n;
        boolean bl = object[n2] != e;
        if (bl) {
            ++this.size;
        }
        return bl;
    }

    @Override
    void addAll() {
        int n;
        long[] arrl;
        for (n = 0; n < (arrl = this.elements).length; ++n) {
            arrl[n] = -1L;
        }
        n = arrl.length - 1;
        arrl[n] = arrl[n] >>> -this.universe.length;
        this.size = this.universe.length;
    }

    @Override
    public boolean addAll(Collection<? extends E> jumboEnumSet) {
        long[] arrl;
        if (!(jumboEnumSet instanceof JumboEnumSet)) {
            return super.addAll(jumboEnumSet);
        }
        jumboEnumSet = jumboEnumSet;
        if (jumboEnumSet.elementType != this.elementType) {
            if (jumboEnumSet.isEmpty()) {
                return false;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(jumboEnumSet.elementType);
            stringBuilder.append(" != ");
            stringBuilder.append(this.elementType);
            throw new ClassCastException(stringBuilder.toString());
        }
        for (int i = 0; i < (arrl = this.elements).length; ++i) {
            arrl[i] = arrl[i] | jumboEnumSet.elements[i];
        }
        return this.recalculateSize();
    }

    @Override
    void addRange(E e, E e2) {
        int n;
        int n2 = ((Enum)e).ordinal() >>> 6;
        if (n2 == (n = ((Enum)e2).ordinal() >>> 6)) {
            this.elements[n2] = -1L >>> ((Enum)e).ordinal() - ((Enum)e2).ordinal() - 1 << ((Enum)e).ordinal();
        } else {
            this.elements[n2] = -1L << ((Enum)e).ordinal();
            ++n2;
            while (n2 < n) {
                this.elements[n2] = -1L;
                ++n2;
            }
            this.elements[n] = -1L >>> 63 - ((Enum)e2).ordinal();
        }
        this.size = ((Enum)e2).ordinal() - ((Enum)e).ordinal() + 1;
    }

    @Override
    public void clear() {
        Arrays.fill(this.elements, 0L);
        this.size = 0;
    }

    @Override
    public EnumSet<E> clone() {
        JumboEnumSet jumboEnumSet = (JumboEnumSet)super.clone();
        jumboEnumSet.elements = (long[])jumboEnumSet.elements.clone();
        return jumboEnumSet;
    }

    @Override
    void complement() {
        int n;
        long[] arrl;
        for (n = 0; n < (arrl = this.elements).length; ++n) {
            arrl[n] = arrl[n];
        }
        n = arrl.length - 1;
        arrl[n] = arrl[n] & -1L >>> -this.universe.length;
        this.size = this.universe.length - this.size;
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
        int n = ((Enum)object).ordinal();
        if ((this.elements[n >>> 6] & 1L << n) != 0L) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean containsAll(Collection<?> jumboEnumSet) {
        long[] arrl;
        if (!(jumboEnumSet instanceof JumboEnumSet)) {
            return super.containsAll(jumboEnumSet);
        }
        jumboEnumSet = jumboEnumSet;
        if (jumboEnumSet.elementType != this.elementType) {
            return jumboEnumSet.isEmpty();
        }
        for (int i = 0; i < (arrl = this.elements).length; ++i) {
            if ((jumboEnumSet.elements[i] & arrl[i]) == 0L) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JumboEnumSet)) {
            return super.equals(object);
        }
        object = (JumboEnumSet)object;
        if (((JumboEnumSet)object).elementType != this.elementType) {
            boolean bl = this.size == 0 && ((JumboEnumSet)object).size == 0;
            return bl;
        }
        return Arrays.equals(((JumboEnumSet)object).elements, this.elements);
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.size == 0;
        return bl;
    }

    @Override
    public Iterator<E> iterator() {
        return new EnumSetIterator();
    }

    @Override
    public boolean remove(Object arrl) {
        boolean bl = false;
        if (arrl == null) {
            return false;
        }
        Class<?> class_ = arrl.getClass();
        if (class_ != this.elementType && class_.getSuperclass() != this.elementType) {
            return false;
        }
        int n = ((Enum)arrl).ordinal();
        int n2 = n >>> 6;
        arrl = this.elements;
        long l = arrl[n2];
        arrl[n2] = arrl[n2] & 1L << n;
        if (arrl[n2] != l) {
            bl = true;
        }
        if (bl) {
            --this.size;
        }
        return bl;
    }

    @Override
    public boolean removeAll(Collection<?> arrl) {
        if (!(arrl instanceof JumboEnumSet)) {
            return super.removeAll((Collection<?>)arrl);
        }
        JumboEnumSet jumboEnumSet = (JumboEnumSet)arrl;
        if (jumboEnumSet.elementType != this.elementType) {
            return false;
        }
        for (int i = 0; i < (arrl = this.elements).length; ++i) {
            arrl[i] = arrl[i] & jumboEnumSet.elements[i];
        }
        return this.recalculateSize();
    }

    @Override
    public boolean retainAll(Collection<?> jumboEnumSet) {
        long[] arrl;
        if (!(jumboEnumSet instanceof JumboEnumSet)) {
            return super.retainAll(jumboEnumSet);
        }
        jumboEnumSet = jumboEnumSet;
        if (jumboEnumSet.elementType != this.elementType) {
            boolean bl = this.size != 0;
            this.clear();
            return bl;
        }
        for (int i = 0; i < (arrl = this.elements).length; ++i) {
            arrl[i] = arrl[i] & jumboEnumSet.elements[i];
        }
        return this.recalculateSize();
    }

    @Override
    public int size() {
        return this.size;
    }

    private class EnumSetIterator<E extends Enum<E>>
    implements Iterator<E> {
        long lastReturned = 0L;
        int lastReturnedIndex = 0;
        long unseen;
        int unseenIndex = 0;

        EnumSetIterator() {
            this.unseen = JumboEnumSet.this.elements[0];
        }

        @Override
        public boolean hasNext() {
            boolean bl;
            do {
                int n;
                long l = this.unseen;
                bl = true;
                if (l != 0L || this.unseenIndex >= JumboEnumSet.this.elements.length - 1) break;
                long[] arrl = JumboEnumSet.this.elements;
                this.unseenIndex = n = this.unseenIndex + 1;
                this.unseen = arrl[n];
            } while (true);
            if (this.unseen == 0L) {
                bl = false;
            }
            return bl;
        }

        @Override
        public E next() {
            if (this.hasNext()) {
                long l = this.unseen;
                this.lastReturned = -l & l;
                this.lastReturnedIndex = this.unseenIndex;
                this.unseen = l - this.lastReturned;
                return (E)JumboEnumSet.this.universe[(this.lastReturnedIndex << 6) + Long.numberOfTrailingZeros(this.lastReturned)];
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            if (this.lastReturned != 0L) {
                long l = JumboEnumSet.this.elements[this.lastReturnedIndex];
                long[] arrl = JumboEnumSet.this.elements;
                int n = this.lastReturnedIndex;
                arrl[n] = arrl[n] & this.lastReturned;
                if (l != JumboEnumSet.this.elements[this.lastReturnedIndex]) {
                    JumboEnumSet.access$110(JumboEnumSet.this);
                }
                this.lastReturned = 0L;
                return;
            }
            throw new IllegalStateException();
        }
    }

}

