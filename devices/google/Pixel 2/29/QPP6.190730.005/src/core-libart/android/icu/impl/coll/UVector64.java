/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

public final class UVector64 {
    private long[] buffer = new long[32];
    private int length = 0;

    private void ensureAppendCapacity() {
        int n = this.length;
        long[] arrl = this.buffer;
        if (n >= arrl.length) {
            n = arrl.length <= 65535 ? arrl.length * 4 : arrl.length * 2;
            arrl = new long[n];
            System.arraycopy(this.buffer, 0, arrl, 0, this.length);
            this.buffer = arrl;
        }
    }

    public void addElement(long l) {
        this.ensureAppendCapacity();
        long[] arrl = this.buffer;
        int n = this.length;
        this.length = n + 1;
        arrl[n] = l;
    }

    public long elementAti(int n) {
        return this.buffer[n];
    }

    public long[] getBuffer() {
        return this.buffer;
    }

    public void insertElementAt(long l, int n) {
        this.ensureAppendCapacity();
        long[] arrl = this.buffer;
        System.arraycopy(arrl, n, arrl, n + 1, this.length - n);
        this.buffer[n] = l;
        ++this.length;
    }

    public boolean isEmpty() {
        boolean bl = this.length == 0;
        return bl;
    }

    public void removeAllElements() {
        this.length = 0;
    }

    public void setElementAt(long l, int n) {
        this.buffer[n] = l;
    }

    public int size() {
        return this.length;
    }
}

