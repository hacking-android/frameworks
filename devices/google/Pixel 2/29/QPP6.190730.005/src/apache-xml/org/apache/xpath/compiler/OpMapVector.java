/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.compiler;

public class OpMapVector {
    protected int m_blocksize;
    protected int m_lengthPos = 0;
    protected int[] m_map;
    protected int m_mapSize;

    public OpMapVector(int n, int n2, int n3) {
        this.m_blocksize = n2;
        this.m_mapSize = n;
        this.m_lengthPos = n3;
        this.m_map = new int[n];
    }

    public final int elementAt(int n) {
        return this.m_map[n];
    }

    public final void setElementAt(int n, int n2) {
        int n3 = this.m_mapSize;
        if (n2 >= n3) {
            int n4 = this.m_mapSize;
            this.m_mapSize = n3 + this.m_blocksize;
            int[] arrn = new int[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrn, 0, n4);
            this.m_map = arrn;
        }
        this.m_map[n2] = n;
    }

    public final void setToSize(int n) {
        int[] arrn = new int[n];
        int[] arrn2 = this.m_map;
        System.arraycopy(arrn2, 0, arrn, 0, arrn2[this.m_lengthPos]);
        this.m_mapSize = n;
        this.m_map = arrn;
    }
}

