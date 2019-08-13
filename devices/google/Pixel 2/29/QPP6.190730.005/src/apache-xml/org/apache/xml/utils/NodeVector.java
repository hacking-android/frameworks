/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.io.Serializable;

public class NodeVector
implements Serializable,
Cloneable {
    static final long serialVersionUID = -713473092200731870L;
    private int m_blocksize;
    protected int m_firstFree = 0;
    private int[] m_map;
    private int m_mapSize;

    public NodeVector() {
        this.m_blocksize = 32;
        this.m_mapSize = 0;
    }

    public NodeVector(int n) {
        this.m_blocksize = n;
        this.m_mapSize = 0;
    }

    public void RemoveAllNoClear() {
        if (this.m_map == null) {
            return;
        }
        this.m_firstFree = 0;
    }

    public void addElement(int n) {
        int[] arrn;
        int n2 = this.m_firstFree;
        int n3 = this.m_mapSize;
        if (n2 + 1 >= n3) {
            arrn = this.m_map;
            if (arrn == null) {
                n3 = this.m_blocksize;
                this.m_map = new int[n3];
                this.m_mapSize = n3;
            } else {
                this.m_mapSize = n3 + this.m_blocksize;
                int[] arrn2 = new int[this.m_mapSize];
                System.arraycopy(arrn, 0, arrn2, 0, n2 + 1);
                this.m_map = arrn2;
            }
        }
        arrn = this.m_map;
        n3 = this.m_firstFree;
        arrn[n3] = n;
        this.m_firstFree = n3 + 1;
    }

    public void appendNodes(NodeVector nodeVector) {
        int n = nodeVector.size();
        int[] arrn = this.m_map;
        if (arrn == null) {
            this.m_mapSize = this.m_blocksize + n;
            this.m_map = new int[this.m_mapSize];
        } else {
            int n2 = this.m_firstFree;
            int n3 = this.m_mapSize;
            if (n2 + n >= n3) {
                this.m_mapSize = n3 + (this.m_blocksize + n);
                int[] arrn2 = new int[this.m_mapSize];
                System.arraycopy(arrn, 0, arrn2, 0, n2 + n);
                this.m_map = arrn2;
            }
        }
        System.arraycopy(nodeVector.m_map, 0, this.m_map, this.m_firstFree, n);
        this.m_firstFree += n;
    }

    public Object clone() throws CloneNotSupportedException {
        NodeVector nodeVector = (NodeVector)super.clone();
        int[] arrn = this.m_map;
        if (arrn != null && arrn == nodeVector.m_map) {
            nodeVector.m_map = new int[arrn.length];
            arrn = this.m_map;
            System.arraycopy(arrn, 0, nodeVector.m_map, 0, arrn.length);
        }
        return nodeVector;
    }

    public boolean contains(int n) {
        if (this.m_map == null) {
            return false;
        }
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (this.m_map[i] != n) continue;
            return true;
        }
        return false;
    }

    public int elementAt(int n) {
        int[] arrn = this.m_map;
        if (arrn == null) {
            return -1;
        }
        return arrn[n];
    }

    public int indexOf(int n) {
        if (this.m_map == null) {
            return -1;
        }
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (this.m_map[i] != n) continue;
            return i;
        }
        return -1;
    }

    public int indexOf(int n, int n2) {
        if (this.m_map == null) {
            return -1;
        }
        while (n2 < this.m_firstFree) {
            if (this.m_map[n2] == n) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public void insertElementAt(int n, int n2) {
        int n3;
        int[] arrn = this.m_map;
        if (arrn == null) {
            n3 = this.m_blocksize;
            this.m_map = new int[n3];
            this.m_mapSize = n3;
        } else {
            n3 = this.m_firstFree;
            int n4 = this.m_mapSize;
            if (n3 + 1 >= n4) {
                this.m_mapSize = n4 + this.m_blocksize;
                int[] arrn2 = new int[this.m_mapSize];
                System.arraycopy(arrn, 0, arrn2, 0, n3 + 1);
                this.m_map = arrn2;
            }
        }
        n3 = this.m_firstFree;
        if (n2 <= n3 - 1) {
            arrn = this.m_map;
            System.arraycopy(arrn, n2, arrn, n2 + 1, n3 - n2);
        }
        this.m_map[n2] = n;
        ++this.m_firstFree;
    }

    public void insertInOrder(int n) {
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (n >= this.m_map[i]) continue;
            this.insertElementAt(n, i);
            return;
        }
        this.addElement(n);
    }

    public final int peepOrNull() {
        int n;
        int[] arrn = this.m_map;
        n = arrn != null && (n = this.m_firstFree) > 0 ? arrn[n - 1] : -1;
        return n;
    }

    public final int peepTail() {
        return this.m_map[this.m_firstFree - 1];
    }

    public final int peepTailSub1() {
        return this.m_map[this.m_firstFree - 2];
    }

    public final int pop() {
        --this.m_firstFree;
        int[] arrn = this.m_map;
        int n = this.m_firstFree;
        int n2 = arrn[n];
        arrn[n] = -1;
        return n2;
    }

    public final int popAndTop() {
        --this.m_firstFree;
        int[] arrn = this.m_map;
        int n = this.m_firstFree;
        int n2 = -1;
        arrn[n] = -1;
        if (n != 0) {
            n2 = arrn[n - 1];
        }
        return n2;
    }

    public final void popPair() {
        this.m_firstFree -= 2;
        int[] arrn = this.m_map;
        int n = this.m_firstFree;
        arrn[n] = -1;
        arrn[n + 1] = -1;
    }

    public final void popQuick() {
        --this.m_firstFree;
        this.m_map[this.m_firstFree] = -1;
    }

    public final void push(int n) {
        int n2 = this.m_firstFree;
        int n3 = this.m_mapSize;
        if (n2 + 1 >= n3) {
            int[] arrn = this.m_map;
            if (arrn == null) {
                n3 = this.m_blocksize;
                this.m_map = new int[n3];
                this.m_mapSize = n3;
            } else {
                this.m_mapSize = n3 + this.m_blocksize;
                int[] arrn2 = new int[this.m_mapSize];
                System.arraycopy(arrn, 0, arrn2, 0, n2 + 1);
                this.m_map = arrn2;
            }
        }
        this.m_map[n2] = n;
        this.m_firstFree = n2 + 1;
    }

    public final void pushPair(int n, int n2) {
        int n3;
        int[] arrn = this.m_map;
        if (arrn == null) {
            n3 = this.m_blocksize;
            this.m_map = new int[n3];
            this.m_mapSize = n3;
        } else {
            int n4 = this.m_firstFree;
            n3 = this.m_mapSize;
            if (n4 + 2 >= n3) {
                this.m_mapSize = n3 + this.m_blocksize;
                int[] arrn2 = new int[this.m_mapSize];
                System.arraycopy(arrn, 0, arrn2, 0, n4);
                this.m_map = arrn2;
            }
        }
        arrn = this.m_map;
        n3 = this.m_firstFree;
        arrn[n3] = n;
        arrn[n3 + 1] = n2;
        this.m_firstFree = n3 + 2;
    }

    public void removeAllElements() {
        if (this.m_map == null) {
            return;
        }
        for (int i = 0; i < this.m_firstFree; ++i) {
            this.m_map[i] = -1;
        }
        this.m_firstFree = 0;
    }

    public boolean removeElement(int n) {
        int n2;
        if (this.m_map == null) {
            return false;
        }
        for (int i = 0; i < (n2 = this.m_firstFree); ++i) {
            int[] arrn = this.m_map;
            if (arrn[i] != n) continue;
            if (i > n2) {
                System.arraycopy(arrn, i + 1, arrn, i - 1, n2 - i);
            } else {
                arrn[i] = -1;
            }
            --this.m_firstFree;
            return true;
        }
        return false;
    }

    public void removeElementAt(int n) {
        int[] arrn = this.m_map;
        if (arrn == null) {
            return;
        }
        int n2 = this.m_firstFree;
        if (n > n2) {
            System.arraycopy(arrn, n + 1, arrn, n - 1, n2 - n);
        } else {
            arrn[n] = -1;
        }
    }

    public void setElementAt(int n, int n2) {
        if (this.m_map == null) {
            int n3 = this.m_blocksize;
            this.m_map = new int[n3];
            this.m_mapSize = n3;
        }
        if (n2 == -1) {
            this.addElement(n);
        }
        this.m_map[n2] = n;
    }

    public final void setTail(int n) {
        this.m_map[this.m_firstFree - 1] = n;
    }

    public final void setTailSub1(int n) {
        this.m_map[this.m_firstFree - 2] = n;
    }

    public int size() {
        return this.m_firstFree;
    }

    public void sort() throws Exception {
        this.sort(this.m_map, 0, this.m_firstFree - 1);
    }

    public void sort(int[] arrn, int n, int n2) throws Exception {
        int n3 = n;
        int n4 = n2;
        if (n3 >= n4) {
            return;
        }
        if (n3 == n4 - 1) {
            if (arrn[n3] > arrn[n4]) {
                n = arrn[n3];
                arrn[n3] = arrn[n4];
                arrn[n4] = n;
            }
            return;
        }
        int n5 = arrn[(n3 + n4) / 2];
        arrn[(n3 + n4) / 2] = arrn[n4];
        arrn[n4] = n5;
        while (n3 < n4) {
            int n6;
            int n7 = n3;
            do {
                n6 = n4;
                if (arrn[n7] > n5) break;
                n6 = n4;
                if (n7 >= n4) break;
                ++n7;
            } while (true);
            while (n5 <= arrn[n6] && n7 < n6) {
                --n6;
            }
            n3 = n7;
            n4 = n6;
            if (n7 >= n6) continue;
            n4 = arrn[n7];
            arrn[n7] = arrn[n6];
            arrn[n6] = n4;
            n3 = n7;
            n4 = n6;
        }
        arrn[n2] = arrn[n4];
        arrn[n4] = n5;
        this.sort(arrn, n, n3 - 1);
        this.sort(arrn, n4 + 1, n2);
    }
}

