/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

public class DecimalToRoman {
    public String m_postLetter;
    public long m_postValue;
    public String m_preLetter;
    public long m_preValue;

    public DecimalToRoman(long l, String string, long l2, String string2) {
        this.m_postValue = l;
        this.m_postLetter = string;
        this.m_preValue = l2;
        this.m_preLetter = string2;
    }
}

