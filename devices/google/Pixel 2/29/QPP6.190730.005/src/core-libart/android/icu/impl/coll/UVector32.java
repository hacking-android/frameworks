/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

public final class UVector32 {
    private int[] buffer = new int[32];
    private int length = 0;

    private void ensureAppendCapacity() {
        int n = this.length;
        int[] arrn = this.buffer;
        if (n >= arrn.length) {
            n = arrn.length <= 65535 ? arrn.length * 4 : arrn.length * 2;
            arrn = new int[n];
            System.arraycopy(this.buffer, 0, arrn, 0, this.length);
            this.buffer = arrn;
        }
    }

    public void addElement(int n) {
        this.ensureAppendCapacity();
        int[] arrn = this.buffer;
        int n2 = this.length;
        this.length = n2 + 1;
        arrn[n2] = n;
    }

    public int elementAti(int n) {
        return this.buffer[n];
    }

    public int[] getBuffer() {
        return this.buffer;
    }

    public void insertElementAt(int n, int n2) {
        this.ensureAppendCapacity();
        int[] arrn = this.buffer;
        System.arraycopy(arrn, n2, arrn, n2 + 1, this.length - n2);
        this.buffer[n2] = n;
        ++this.length;
    }

    public boolean isEmpty() {
        boolean bl = this.length == 0;
        return bl;
    }

    public void removeAllElements() {
        this.length = 0;
    }

    public void setElementAt(int n, int n2) {
        this.buffer[n2] = n;
    }

    public int size() {
        return this.length;
    }
}

