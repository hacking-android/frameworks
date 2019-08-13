/*
 * Decompiled with CFR 0.145.
 */
package android.sax;

import android.sax.Element;

class Children {
    Child[] children = new Child[16];

    Children() {
    }

    Element get(String string2, String string3) {
        Child child;
        int n = string2.hashCode() * 31 + string3.hashCode();
        Child child2 = child = this.children[n & 15];
        if (child == null) {
            return null;
        }
        do {
            if (child2.hash != n || child2.uri.compareTo(string2) != 0 || child2.localName.compareTo(string3) != 0) continue;
            return child2;
        } while ((child2 = child2.next) != null);
        return null;
    }

    Element getOrCreate(Element element, String string2, String string3) {
        Child child;
        int n = string2.hashCode() * 31 + string3.hashCode();
        int n2 = n & 15;
        Child child2 = child = this.children[n2];
        if (child == null) {
            element = new Child(element, string2, string3, element.depth + 1, n);
            this.children[n2] = element;
            return element;
        }
        while (child2.hash != n || child2.uri.compareTo(string2) != 0 || child2.localName.compareTo(string3) != 0) {
            child = child2.next;
            if (child == null) {
                element = new Child(element, string2, string3, element.depth + 1, n);
                child2.next = element;
                return element;
            }
            child2 = child;
        }
        return child2;
    }

    static class Child
    extends Element {
        final int hash;
        Child next;

        Child(Element element, String string2, String string3, int n, int n2) {
            super(element, string2, string3, n);
            this.hash = n2;
        }
    }

}

