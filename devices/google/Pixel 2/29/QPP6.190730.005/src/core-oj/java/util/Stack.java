/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.EmptyStackException;
import java.util.Vector;

public class Stack<E>
extends Vector<E> {
    private static final long serialVersionUID = 1224463164541339165L;

    public boolean empty() {
        boolean bl = this.size() == 0;
        return bl;
    }

    public E peek() {
        synchronized (this) {
            block5 : {
                int n = this.size();
                if (n == 0) break block5;
                Object e = this.elementAt(n - 1);
                return e;
            }
            EmptyStackException emptyStackException = new EmptyStackException();
            throw emptyStackException;
        }
    }

    public E pop() {
        synchronized (this) {
            int n = this.size();
            E e = this.peek();
            this.removeElementAt(n - 1);
            return e;
        }
    }

    public E push(E e) {
        this.addElement(e);
        return e;
    }

    public int search(Object object) {
        synchronized (this) {
            block4 : {
                int n = this.lastIndexOf(object);
                if (n < 0) break block4;
                int n2 = this.size();
                return n2 - n;
            }
            return -1;
        }
    }
}

