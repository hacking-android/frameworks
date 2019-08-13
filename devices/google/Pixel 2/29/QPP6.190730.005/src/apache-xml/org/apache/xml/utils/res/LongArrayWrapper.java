/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

public class LongArrayWrapper {
    private long[] m_long;

    public LongArrayWrapper(long[] arrl) {
        this.m_long = arrl;
    }

    public int getLength() {
        return this.m_long.length;
    }

    public long getLong(int n) {
        return this.m_long[n];
    }
}

