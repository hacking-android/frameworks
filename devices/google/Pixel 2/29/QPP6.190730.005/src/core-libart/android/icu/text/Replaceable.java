/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

public interface Replaceable {
    public int char32At(int var1);

    public char charAt(int var1);

    public void copy(int var1, int var2, int var3);

    public void getChars(int var1, int var2, char[] var3, int var4);

    public boolean hasMetaData();

    public int length();

    public void replace(int var1, int var2, String var3);

    public void replace(int var1, int var2, char[] var3, int var4, int var5);
}

