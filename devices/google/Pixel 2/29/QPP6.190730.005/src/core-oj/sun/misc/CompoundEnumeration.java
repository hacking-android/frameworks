/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class CompoundEnumeration<E>
implements Enumeration<E> {
    private Enumeration<E>[] enums;
    private int index = 0;

    public CompoundEnumeration(Enumeration<E>[] arrenumeration) {
        this.enums = arrenumeration;
    }

    private boolean next() {
        int n;
        Enumeration<E>[] arrenumeration;
        while ((n = ++this.index) < (arrenumeration = this.enums).length) {
            if (arrenumeration[n] == null || !arrenumeration[n].hasMoreElements()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasMoreElements() {
        return this.next();
    }

    @Override
    public E nextElement() {
        if (this.next()) {
            return this.enums[this.index].nextElement();
        }
        throw new NoSuchElementException();
    }
}

