/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

public class StringToIntTable {
    public static final int INVALID_KEY = -10000;
    private int m_blocksize;
    private int m_firstFree = 0;
    private String[] m_map;
    private int m_mapSize;
    private int[] m_values;

    public StringToIntTable() {
        int n;
        this.m_mapSize = n = (this.m_blocksize = 8);
        this.m_map = new String[n];
        this.m_values = new int[n];
    }

    public StringToIntTable(int n) {
        this.m_blocksize = n;
        this.m_mapSize = n;
        this.m_map = new String[n];
        this.m_values = new int[this.m_blocksize];
    }

    public final boolean contains(String string) {
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (!this.m_map[i].equals(string)) continue;
            return true;
        }
        return false;
    }

    public final int get(String string) {
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (!this.m_map[i].equals(string)) continue;
            return this.m_values[i];
        }
        return -10000;
    }

    public final int getIgnoreCase(String string) {
        if (string == null) {
            return -10000;
        }
        for (int i = 0; i < this.m_firstFree; ++i) {
            if (!this.m_map[i].equalsIgnoreCase(string)) continue;
            return this.m_values[i];
        }
        return -10000;
    }

    public final int getLength() {
        return this.m_firstFree;
    }

    public final String[] keys() {
        String[] arrstring = new String[this.m_firstFree];
        for (int i = 0; i < this.m_firstFree; ++i) {
            arrstring[i] = this.m_map[i];
        }
        return arrstring;
    }

    public final void put(String string, int n) {
        Object[] arrobject;
        int n2 = this.m_firstFree;
        int n3 = this.m_mapSize;
        if (n2 + 1 >= n3) {
            this.m_mapSize = n3 + this.m_blocksize;
            arrobject = new String[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrobject, 0, n2 + 1);
            this.m_map = arrobject;
            arrobject = new int[this.m_mapSize];
            System.arraycopy(this.m_values, 0, arrobject, 0, this.m_firstFree + 1);
            this.m_values = arrobject;
        }
        arrobject = this.m_map;
        n2 = this.m_firstFree;
        arrobject[n2] = string;
        this.m_values[n2] = n;
        this.m_firstFree = n2 + 1;
    }
}

