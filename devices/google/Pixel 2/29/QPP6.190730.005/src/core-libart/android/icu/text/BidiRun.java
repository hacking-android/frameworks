/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

public class BidiRun {
    int insertRemove;
    byte level;
    int limit;
    int start;

    BidiRun() {
        this(0, 0, 0);
    }

    BidiRun(int n, int n2, byte by) {
        this.start = n;
        this.limit = n2;
        this.level = by;
    }

    void copyFrom(BidiRun bidiRun) {
        this.start = bidiRun.start;
        this.limit = bidiRun.limit;
        this.level = bidiRun.level;
        this.insertRemove = bidiRun.insertRemove;
    }

    public byte getDirection() {
        return (byte)(this.level & 1);
    }

    public byte getEmbeddingLevel() {
        return this.level;
    }

    public int getLength() {
        return this.limit - this.start;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getStart() {
        return this.start;
    }

    public boolean isEvenRun() {
        byte by = this.level;
        boolean bl = true;
        if ((by & 1) != 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isOddRun() {
        byte by = this.level;
        boolean bl = true;
        if ((by & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BidiRun ");
        stringBuilder.append(this.start);
        stringBuilder.append(" - ");
        stringBuilder.append(this.limit);
        stringBuilder.append(" @ ");
        stringBuilder.append(this.level);
        return stringBuilder.toString();
    }
}

