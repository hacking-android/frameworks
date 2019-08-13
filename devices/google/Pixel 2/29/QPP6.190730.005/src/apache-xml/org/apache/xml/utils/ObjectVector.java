/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

public class ObjectVector
implements Cloneable {
    protected int m_blocksize;
    protected int m_firstFree = 0;
    protected Object[] m_map;
    protected int m_mapSize;

    public ObjectVector() {
        int n;
        this.m_mapSize = n = (this.m_blocksize = 32);
        this.m_map = new Object[n];
    }

    public ObjectVector(int n) {
        this.m_blocksize = n;
        this.m_mapSize = n;
        this.m_map = new Object[n];
    }

    public ObjectVector(int n, int n2) {
        this.m_blocksize = n2;
        this.m_mapSize = n;
        this.m_map = new Object[n];
    }

    public ObjectVector(ObjectVector objectVector) {
        int n = objectVector.m_mapSize;
        this.m_map = new Object[n];
        this.m_mapSize = n;
        this.m_firstFree = objectVector.m_firstFree;
        this.m_blocksize = objectVector.m_blocksize;
        System.arraycopy(objectVector.m_map, 0, this.m_map, 0, this.m_firstFree);
    }

    public final void addElement(Object object) {
        Object[] arrobject;
        int n = this.m_firstFree;
        int n2 = this.m_mapSize;
        if (n + 1 >= n2) {
            this.m_mapSize = n2 + this.m_blocksize;
            arrobject = new Object[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrobject, 0, n + 1);
            this.m_map = arrobject;
        }
        arrobject = this.m_map;
        n2 = this.m_firstFree;
        arrobject[n2] = object;
        this.m_firstFree = n2 + 1;
    }

    public final void addElements(int n) {
        int n2 = this.m_firstFree;
        int n3 = this.m_mapSize;
        if (n2 + n >= n3) {
            this.m_mapSize = n3 + (this.m_blocksize + n);
            Object[] arrobject = new Object[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrobject, 0, n2 + 1);
            this.m_map = arrobject;
        }
        this.m_firstFree += n;
    }

    public final void addElements(Object object, int n) {
        Object[] arrobject;
        int n2 = this.m_firstFree;
        int n3 = this.m_mapSize;
        if (n2 + n >= n3) {
            this.m_mapSize = n3 + (this.m_blocksize + n);
            arrobject = new Object[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrobject, 0, n2 + 1);
            this.m_map = arrobject;
        }
        for (n3 = 0; n3 < n; ++n3) {
            arrobject = this.m_map;
            n2 = this.m_firstFree;
            arrobject[n2] = object;
            this.m_firstFree = n2 + 1;
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return new ObjectVector(this);
    }

    public final boolean contains(Object object) {
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (this.m_map[i] != object) continue;
            return true;
        }
        return false;
    }

    public final Object elementAt(int n) {
        return this.m_map[n];
    }

    public final int indexOf(Object object) {
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (this.m_map[i] != object) continue;
            return i;
        }
        return Integer.MIN_VALUE;
    }

    public final int indexOf(Object object, int n) {
        while (n < this.m_firstFree) {
            if (this.m_map[n] == object) {
                return n;
            }
            ++n;
        }
        return Integer.MIN_VALUE;
    }

    public final void insertElementAt(Object object, int n) {
        Object[] arrobject;
        int n2 = this.m_firstFree;
        int n3 = this.m_mapSize;
        if (n2 + 1 >= n3) {
            this.m_mapSize = n3 + this.m_blocksize;
            arrobject = new Object[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrobject, 0, n2 + 1);
            this.m_map = arrobject;
        }
        if (n <= (n2 = this.m_firstFree) - 1) {
            arrobject = this.m_map;
            System.arraycopy(arrobject, n, arrobject, n + 1, n2 - n);
        }
        this.m_map[n] = object;
        ++this.m_firstFree;
    }

    public final int lastIndexOf(Object object) {
        for (int i = this.m_firstFree - 1; i >= 0; --i) {
            if (this.m_map[i] != object) continue;
            return i;
        }
        return Integer.MIN_VALUE;
    }

    public final void removeAllElements() {
        for (int i = 0; i < this.m_firstFree; ++i) {
            this.m_map[i] = null;
        }
        this.m_firstFree = 0;
    }

    public final boolean removeElement(Object object) {
        int n;
        for (int i = 0; i < (n = this.m_firstFree); ++i) {
            Object[] arrobject = this.m_map;
            if (arrobject[i] != object) continue;
            if (i + 1 < n) {
                System.arraycopy(arrobject, i + 1, arrobject, i - 1, n - i);
            } else {
                arrobject[i] = null;
            }
            --this.m_firstFree;
            return true;
        }
        return false;
    }

    public final void removeElementAt(int n) {
        int n2 = this.m_firstFree;
        if (n > n2) {
            Object[] arrobject = this.m_map;
            System.arraycopy(arrobject, n + 1, arrobject, n, n2);
        } else {
            this.m_map[n] = null;
        }
        --this.m_firstFree;
    }

    public final void setElementAt(Object object, int n) {
        this.m_map[n] = object;
    }

    public final void setSize(int n) {
        this.m_firstFree = n;
    }

    public final void setToSize(int n) {
        Object[] arrobject = new Object[n];
        System.arraycopy(this.m_map, 0, arrobject, 0, this.m_firstFree);
        this.m_mapSize = n;
        this.m_map = arrobject;
    }

    public final int size() {
        return this.m_firstFree;
    }
}

