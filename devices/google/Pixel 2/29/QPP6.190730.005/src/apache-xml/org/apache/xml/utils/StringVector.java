/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.io.Serializable;

public class StringVector
implements Serializable {
    static final long serialVersionUID = 4995234972032919748L;
    protected int m_blocksize;
    protected int m_firstFree = 0;
    protected String[] m_map;
    protected int m_mapSize;

    public StringVector() {
        int n;
        this.m_mapSize = n = (this.m_blocksize = 8);
        this.m_map = new String[n];
    }

    public StringVector(int n) {
        this.m_blocksize = n;
        this.m_mapSize = n;
        this.m_map = new String[n];
    }

    public final void addElement(String string) {
        String[] arrstring;
        int n = this.m_firstFree;
        int n2 = this.m_mapSize;
        if (n + 1 >= n2) {
            this.m_mapSize = n2 + this.m_blocksize;
            arrstring = new String[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrstring, 0, n + 1);
            this.m_map = arrstring;
        }
        arrstring = this.m_map;
        n = this.m_firstFree;
        arrstring[n] = string;
        this.m_firstFree = n + 1;
    }

    public final boolean contains(String string) {
        if (string == null) {
            return false;
        }
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (!this.m_map[i].equals(string)) continue;
            return true;
        }
        return false;
    }

    public final boolean containsIgnoreCase(String string) {
        if (string == null) {
            return false;
        }
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (!this.m_map[i].equalsIgnoreCase(string)) continue;
            return true;
        }
        return false;
    }

    public final String elementAt(int n) {
        return this.m_map[n];
    }

    public int getLength() {
        return this.m_firstFree;
    }

    public final String peek() {
        int n = this.m_firstFree;
        String string = n <= 0 ? null : this.m_map[n - 1];
        return string;
    }

    public final String pop() {
        int n = this.m_firstFree;
        if (n <= 0) {
            return null;
        }
        this.m_firstFree = n - 1;
        String[] arrstring = this.m_map;
        n = this.m_firstFree;
        String string = arrstring[n];
        arrstring[n] = null;
        return string;
    }

    public final void push(String string) {
        String[] arrstring;
        int n = this.m_firstFree;
        int n2 = this.m_mapSize;
        if (n + 1 >= n2) {
            this.m_mapSize = n2 + this.m_blocksize;
            arrstring = new String[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrstring, 0, n + 1);
            this.m_map = arrstring;
        }
        arrstring = this.m_map;
        n = this.m_firstFree;
        arrstring[n] = string;
        this.m_firstFree = n + 1;
    }

    public final int size() {
        return this.m_firstFree;
    }
}

