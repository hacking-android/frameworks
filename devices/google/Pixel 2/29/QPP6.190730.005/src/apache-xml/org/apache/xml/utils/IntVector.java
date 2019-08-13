/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

public class IntVector
implements Cloneable {
    protected int m_blocksize;
    protected int m_firstFree = 0;
    protected int[] m_map;
    protected int m_mapSize;

    public IntVector() {
        int n;
        this.m_mapSize = n = (this.m_blocksize = 32);
        this.m_map = new int[n];
    }

    public IntVector(int n) {
        this.m_blocksize = n;
        this.m_mapSize = n;
        this.m_map = new int[n];
    }

    public IntVector(int n, int n2) {
        this.m_blocksize = n2;
        this.m_mapSize = n;
        this.m_map = new int[n];
    }

    public IntVector(IntVector intVector) {
        int n = intVector.m_mapSize;
        this.m_map = new int[n];
        this.m_mapSize = n;
        this.m_firstFree = intVector.m_firstFree;
        this.m_blocksize = intVector.m_blocksize;
        System.arraycopy(intVector.m_map, 0, this.m_map, 0, this.m_firstFree);
    }

    public final void addElement(int n) {
        int[] arrn;
        int n2 = this.m_firstFree;
        int n3 = this.m_mapSize;
        if (n2 + 1 >= n3) {
            this.m_mapSize = n3 + this.m_blocksize;
            arrn = new int[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrn, 0, n2 + 1);
            this.m_map = arrn;
        }
        arrn = this.m_map;
        n3 = this.m_firstFree;
        arrn[n3] = n;
        this.m_firstFree = n3 + 1;
    }

    public final void addElements(int n) {
        int n2 = this.m_firstFree;
        int n3 = this.m_mapSize;
        if (n2 + n >= n3) {
            this.m_mapSize = n3 + (this.m_blocksize + n);
            int[] arrn = new int[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrn, 0, n2 + 1);
            this.m_map = arrn;
        }
        this.m_firstFree += n;
    }

    public final void addElements(int n, int n2) {
        int[] arrn;
        int n3 = this.m_firstFree;
        int n4 = this.m_mapSize;
        if (n3 + n2 >= n4) {
            this.m_mapSize = n4 + (this.m_blocksize + n2);
            arrn = new int[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrn, 0, n3 + 1);
            this.m_map = arrn;
        }
        for (n3 = 0; n3 < n2; ++n3) {
            arrn = this.m_map;
            n4 = this.m_firstFree;
            arrn[n4] = n;
            this.m_firstFree = n4 + 1;
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return new IntVector(this);
    }

    public final boolean contains(int n) {
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (this.m_map[i] != n) continue;
            return true;
        }
        return false;
    }

    public final int elementAt(int n) {
        return this.m_map[n];
    }

    public final int indexOf(int n) {
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (this.m_map[i] != n) continue;
            return i;
        }
        return Integer.MIN_VALUE;
    }

    public final int indexOf(int n, int n2) {
        while (n2 < this.m_firstFree) {
            if (this.m_map[n2] == n) {
                return n2;
            }
            ++n2;
        }
        return Integer.MIN_VALUE;
    }

    public final void insertElementAt(int n, int n2) {
        int[] arrn;
        int n3 = this.m_firstFree;
        int n4 = this.m_mapSize;
        if (n3 + 1 >= n4) {
            this.m_mapSize = n4 + this.m_blocksize;
            arrn = new int[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrn, 0, n3 + 1);
            this.m_map = arrn;
        }
        if (n2 <= (n3 = this.m_firstFree) - 1) {
            arrn = this.m_map;
            System.arraycopy(arrn, n2, arrn, n2 + 1, n3 - n2);
        }
        this.m_map[n2] = n;
        ++this.m_firstFree;
    }

    public final int lastIndexOf(int n) {
        for (int i = this.m_firstFree - 1; i >= 0; --i) {
            if (this.m_map[i] != n) continue;
            return i;
        }
        return Integer.MIN_VALUE;
    }

    public final void removeAllElements() {
        for (int i = 0; i < this.m_firstFree; ++i) {
            this.m_map[i] = Integer.MIN_VALUE;
        }
        this.m_firstFree = 0;
    }

    public final boolean removeElement(int n) {
        int n2;
        for (int i = 0; i < (n2 = this.m_firstFree); ++i) {
            int[] arrn = this.m_map;
            if (arrn[i] != n) continue;
            if (i + 1 < n2) {
                System.arraycopy(arrn, i + 1, arrn, i - 1, n2 - i);
            } else {
                arrn[i] = Integer.MIN_VALUE;
            }
            --this.m_firstFree;
            return true;
        }
        return false;
    }

    public final void removeElementAt(int n) {
        int n2 = this.m_firstFree;
        if (n > n2) {
            int[] arrn = this.m_map;
            System.arraycopy(arrn, n + 1, arrn, n, n2);
        } else {
            this.m_map[n] = Integer.MIN_VALUE;
        }
        --this.m_firstFree;
    }

    public final void setElementAt(int n, int n2) {
        this.m_map[n2] = n;
    }

    public final void setSize(int n) {
        this.m_firstFree = n;
    }

    public final int size() {
        return this.m_firstFree;
    }
}

