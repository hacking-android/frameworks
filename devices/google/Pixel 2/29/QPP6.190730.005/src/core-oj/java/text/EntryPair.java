/*
 * Decompiled with CFR 0.145.
 */
package java.text;

final class EntryPair {
    public String entryName;
    public boolean fwd;
    public int value;

    public EntryPair(String string, int n) {
        this(string, n, true);
    }

    public EntryPair(String string, int n, boolean bl) {
        this.entryName = string;
        this.value = n;
        this.fwd = bl;
    }
}

