/*
 * Decompiled with CFR 0.145.
 */
package java.util.zip;

class ZStreamRef {
    private volatile long address;

    ZStreamRef(long l) {
        this.address = l;
    }

    long address() {
        return this.address;
    }

    void clear() {
        this.address = 0L;
    }
}

