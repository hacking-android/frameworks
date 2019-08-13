/*
 * Decompiled with CFR 0.145.
 */
package android.database;

public final class CharArrayBuffer {
    public char[] data;
    public int sizeCopied;

    public CharArrayBuffer(int n) {
        this.data = new char[n];
    }

    public CharArrayBuffer(char[] arrc) {
        this.data = arrc;
    }
}

