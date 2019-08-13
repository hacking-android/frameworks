/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm;

public abstract class DTMAxisTraverser {
    public int first(int n) {
        return this.next(n, n);
    }

    public int first(int n, int n2) {
        return this.next(n, n, n2);
    }

    public abstract int next(int var1, int var2);

    public abstract int next(int var1, int var2, int var3);
}

