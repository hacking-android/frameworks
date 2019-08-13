/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

public class IntArrayWrapper {
    private int[] m_int;

    public IntArrayWrapper(int[] arrn) {
        this.m_int = arrn;
    }

    public int getInt(int n) {
        return this.m_int[n];
    }

    public int getLength() {
        return this.m_int.length;
    }
}

