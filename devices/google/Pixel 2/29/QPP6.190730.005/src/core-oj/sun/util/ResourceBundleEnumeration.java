/*
 * Decompiled with CFR 0.145.
 */
package sun.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class ResourceBundleEnumeration
implements Enumeration<String> {
    Enumeration<String> enumeration;
    Iterator<String> iterator;
    String next = null;
    Set<String> set;

    public ResourceBundleEnumeration(Set<String> set, Enumeration<String> enumeration) {
        this.set = set;
        this.iterator = set.iterator();
        this.enumeration = enumeration;
    }

    @Override
    public boolean hasMoreElements() {
        if (this.next == null) {
            if (this.iterator.hasNext()) {
                this.next = this.iterator.next();
            } else if (this.enumeration != null) {
                while (this.next == null && this.enumeration.hasMoreElements()) {
                    this.next = this.enumeration.nextElement();
                    if (!this.set.contains(this.next)) continue;
                    this.next = null;
                }
            }
        }
        boolean bl = this.next != null;
        return bl;
    }

    @Override
    public String nextElement() {
        if (this.hasMoreElements()) {
            String string = this.next;
            this.next = null;
            return string;
        }
        throw new NoSuchElementException();
    }
}

