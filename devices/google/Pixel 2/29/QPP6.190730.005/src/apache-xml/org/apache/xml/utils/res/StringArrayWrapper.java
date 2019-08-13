/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

public class StringArrayWrapper {
    private String[] m_string;

    public StringArrayWrapper(String[] arrstring) {
        this.m_string = arrstring;
    }

    public int getLength() {
        return this.m_string.length;
    }

    public String getString(int n) {
        return this.m_string[n];
    }
}

