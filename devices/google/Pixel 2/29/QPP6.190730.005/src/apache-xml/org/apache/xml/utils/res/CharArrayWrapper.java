/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

public class CharArrayWrapper {
    private char[] m_char;

    public CharArrayWrapper(char[] arrc) {
        this.m_char = arrc;
    }

    public char getChar(int n) {
        return this.m_char[n];
    }

    public int getLength() {
        return this.m_char.length;
    }
}

