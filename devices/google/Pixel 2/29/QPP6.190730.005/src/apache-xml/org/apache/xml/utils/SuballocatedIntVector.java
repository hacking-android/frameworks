/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

public class SuballocatedIntVector {
    protected static final int NUMBLOCKS_DEFAULT = 32;
    protected int m_MASK;
    protected int m_SHIFT = 0;
    protected int m_blocksize;
    protected int[] m_buildCache;
    protected int m_buildCacheStartIndex;
    protected int m_firstFree = 0;
    protected int[][] m_map;
    protected int[] m_map0;
    protected int m_numblocks = 32;

    public SuballocatedIntVector() {
        this(2048);
    }

    public SuballocatedIntVector(int n) {
        this(n, 32);
    }

    public SuballocatedIntVector(int n, int n2) {
        do {
            int n3;
            n = n3 = n >>> 1;
            if (n3 == 0) break;
            ++this.m_SHIFT;
        } while (true);
        n = this.m_blocksize = 1 << this.m_SHIFT;
        this.m_MASK = n - 1;
        this.m_numblocks = n2;
        this.m_map0 = new int[n];
        int[][] arrn = this.m_map = new int[n2][];
        int[] arrn2 = this.m_map0;
        arrn[0] = arrn2;
        this.m_buildCache = arrn2;
        this.m_buildCacheStartIndex = 0;
    }

    private void addElements(int n) {
        int n2 = this.m_firstFree;
        int n3 = n2 + n;
        if (n3 > this.m_blocksize) {
            int n4 = this.m_SHIFT;
            for (int i = (n2 >>> n4) + 1; i <= n2 + n >>> n4; ++i) {
                this.m_map[i] = new int[this.m_blocksize];
            }
        }
        this.m_firstFree = n3;
    }

    private void addElements(int n, int n2) {
        int n3 = this.m_firstFree;
        if (n3 + n2 < this.m_blocksize) {
            for (int i = 0; i < n2; ++i) {
                int[] arrn = this.m_map0;
                n3 = this.m_firstFree;
                this.m_firstFree = n3 + 1;
                arrn[n3] = n;
            }
        } else {
            int n4 = n3 >>> this.m_SHIFT;
            int n5 = this.m_MASK & n3;
            this.m_firstFree = n3 + n2;
            while (n2 > 0) {
                int[][] arrarrn;
                int[][] arrn = this.m_map;
                if (n4 >= arrn.length) {
                    arrarrn = new int[this.m_numblocks + n4][];
                    System.arraycopy(arrn, 0, arrarrn, 0, arrn.length);
                    this.m_map = arrarrn;
                }
                int[][] arrn2 = this.m_map;
                arrarrn = arrn2[n4];
                arrn = arrarrn;
                if (arrarrn == null) {
                    arrn = new int[this.m_blocksize];
                    arrn2[n4] = arrn;
                }
                n3 = (n3 = this.m_blocksize) - n5 < n2 ? (n3 -= n5) : n2;
                n2 -= n3;
                while (n3 > 0) {
                    arrn[n5] = (int[])n;
                    ++n5;
                    --n3;
                }
                ++n4;
                n5 = 0;
            }
        }
    }

    private boolean contains(int n) {
        boolean bl = false;
        if (this.indexOf(n, 0) >= 0) {
            bl = true;
        }
        return bl;
    }

    private void insertElementAt(int n, int n2) {
        int n3 = this.m_firstFree;
        if (n2 == n3) {
            this.addElement(n);
        } else if (n2 > n3) {
            int[][] arrarrn;
            int n4 = n2 >>> this.m_SHIFT;
            int[][] arrn = this.m_map;
            if (n4 >= arrn.length) {
                arrarrn = new int[this.m_numblocks + n4][];
                System.arraycopy(arrn, 0, arrarrn, 0, arrn.length);
                this.m_map = arrarrn;
            }
            int[][] arrn2 = this.m_map;
            arrarrn = arrn2[n4];
            arrn = arrarrn;
            if (arrarrn == null) {
                arrn = new int[this.m_blocksize];
                arrn2[n4] = arrn;
            }
            n2 = this.m_MASK & n2;
            arrn[n2] = (int[])n;
            this.m_firstFree = n2 + 1;
        } else {
            int n5 = this.m_SHIFT;
            int n6 = n2 >>> n5;
            this.m_firstFree = n3 + 1;
            int n7 = this.m_MASK & n2;
            int n8 = n;
            for (n2 = n6; n2 <= n3 >>> n5; ++n2) {
                n6 = this.m_blocksize;
                int[][] arrn = this.m_map;
                int[] arrn3 = arrn[n2];
                if (arrn3 == null) {
                    n = 0;
                    arrn3 = new int[n6];
                    arrn[n2] = arrn3;
                } else {
                    n = arrn3[n6 - 1];
                    System.arraycopy(arrn3, n7, arrn3, n7 + 1, n6 - n7 - 1);
                }
                arrn3[n7] = n8;
                n7 = 0;
                n8 = n;
            }
        }
    }

    private int lastIndexOf(int n) {
        int n2 = this.m_firstFree;
        int n3 = this.m_MASK & n2;
        n2 >>>= this.m_SHIFT;
        while (n2 >= 0) {
            int[] arrn = this.m_map[n2];
            if (arrn != null) {
                while (n3 >= 0) {
                    if (arrn[n3] == n) {
                        return this.m_blocksize * n2 + n3;
                    }
                    --n3;
                }
            }
            n3 = 0;
            --n2;
        }
        return -1;
    }

    private boolean removeElement(int n) {
        if ((n = this.indexOf(n, 0)) < 0) {
            return false;
        }
        this.removeElementAt(n);
        return true;
    }

    private void removeElementAt(int n) {
        int n2 = this.m_firstFree;
        if (n < n2) {
            int n3 = this.m_SHIFT;
            int n4 = n >>> n3;
            n3 = n2 >>> n3;
            n2 = this.m_MASK & n;
            n = n4;
            n4 = n2;
            while (n <= n3) {
                n2 = this.m_blocksize;
                int[][] arrn = this.m_map;
                int[] arrn2 = arrn[n];
                if (arrn2 == null) {
                    arrn2 = new int[n2];
                    arrn[n] = arrn2;
                } else {
                    System.arraycopy(arrn2, n4 + 1, arrn2, n4, n2 - n4 - 1);
                }
                if (n < n3) {
                    arrn = this.m_map[n + 1];
                    if (arrn != null) {
                        arrn2[this.m_blocksize - 1] = (int)arrn[0];
                    }
                } else {
                    arrn2[this.m_blocksize - 1] = 0;
                }
                n4 = 0;
                ++n;
            }
        }
        --this.m_firstFree;
    }

    public void addElement(int n) {
        int n2 = this.m_firstFree;
        int n3 = n2 - this.m_buildCacheStartIndex;
        if (n3 >= 0 && n3 < this.m_blocksize) {
            this.m_buildCache[n3] = n;
            this.m_firstFree = n2 + 1;
        } else {
            int[][] arrarrn;
            n2 = this.m_firstFree;
            n3 = n2 >>> this.m_SHIFT;
            n2 &= this.m_MASK;
            int[][] arrn = this.m_map;
            if (n3 >= arrn.length) {
                arrarrn = new int[this.m_numblocks + n3][];
                System.arraycopy(arrn, 0, arrarrn, 0, arrn.length);
                this.m_map = arrarrn;
            }
            int[][] arrn2 = this.m_map;
            arrn = arrn2[n3];
            arrarrn = arrn;
            if (arrn == null) {
                arrarrn = new int[this.m_blocksize];
                arrn2[n3] = arrarrn;
            }
            arrarrn[n2] = (int[])n;
            this.m_buildCache = arrarrn;
            n = this.m_firstFree;
            this.m_buildCacheStartIndex = n - n2;
            this.m_firstFree = n + 1;
        }
    }

    public int elementAt(int n) {
        if (n < this.m_blocksize) {
            return this.m_map0[n];
        }
        return this.m_map[n >>> this.m_SHIFT][this.m_MASK & n];
    }

    public final int[][] getMap() {
        return this.m_map;
    }

    public final int[] getMap0() {
        return this.m_map0;
    }

    public int indexOf(int n) {
        return this.indexOf(n, 0);
    }

    public int indexOf(int n, int n2) {
        int[] arrn;
        int n3 = this.m_firstFree;
        if (n2 >= n3) {
            return -1;
        }
        int n4 = this.m_SHIFT;
        int n5 = n2 >>> n4;
        n2 = this.m_MASK & n2;
        n3 >>>= n4;
        while (n5 < n3) {
            arrn = this.m_map[n5];
            if (arrn != null) {
                while (n2 < (n4 = this.m_blocksize)) {
                    if (arrn[n2] == n) {
                        return n4 * n5 + n2;
                    }
                    ++n2;
                }
            }
            n2 = 0;
            ++n5;
        }
        n4 = this.m_firstFree;
        n5 = this.m_MASK;
        arrn = this.m_map[n3];
        while (n2 < (n4 & n5)) {
            if (arrn[n2] == n) {
                return this.m_blocksize * n3 + n2;
            }
            ++n2;
        }
        return -1;
    }

    public void removeAllElements() {
        this.m_firstFree = 0;
        this.m_buildCache = this.m_map0;
        this.m_buildCacheStartIndex = 0;
    }

    public void setElementAt(int n, int n2) {
        if (n2 < this.m_blocksize) {
            this.m_map0[n2] = n;
        } else {
            int[][] arrarrn;
            int n3 = n2 >>> this.m_SHIFT;
            int n4 = this.m_MASK;
            int[][] arrn = this.m_map;
            if (n3 >= arrn.length) {
                arrarrn = new int[this.m_numblocks + n3][];
                System.arraycopy(arrn, 0, arrarrn, 0, arrn.length);
                this.m_map = arrarrn;
            }
            int[][] arrn2 = this.m_map;
            arrarrn = arrn2[n3];
            arrn = arrarrn;
            if (arrarrn == null) {
                arrn = new int[this.m_blocksize];
                arrn2[n3] = arrn;
            }
            arrn[n4 & n2] = (int[])n;
        }
        if (n2 >= this.m_firstFree) {
            this.m_firstFree = n2 + 1;
        }
    }

    public void setSize(int n) {
        if (this.m_firstFree > n) {
            this.m_firstFree = n;
        }
    }

    public int size() {
        return this.m_firstFree;
    }
}

