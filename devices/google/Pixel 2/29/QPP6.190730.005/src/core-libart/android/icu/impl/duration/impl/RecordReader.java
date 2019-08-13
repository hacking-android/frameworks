/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration.impl;

interface RecordReader {
    public boolean bool(String var1);

    public boolean[] boolArray(String var1);

    public char character(String var1);

    public char[] characterArray(String var1);

    public boolean close();

    public byte namedIndex(String var1, String[] var2);

    public byte[] namedIndexArray(String var1, String[] var2);

    public boolean open(String var1);

    public String string(String var1);

    public String[] stringArray(String var1);

    public String[][] stringTable(String var1);
}

