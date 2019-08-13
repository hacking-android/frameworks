/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

abstract class AbstractSpinedBuffer {
    public static final int MAX_CHUNK_POWER = 30;
    public static final int MIN_CHUNK_POWER = 4;
    public static final int MIN_CHUNK_SIZE = 16;
    public static final int MIN_SPINE_SIZE = 8;
    protected int elementIndex;
    protected final int initialChunkPower;
    protected long[] priorElementCount;
    protected int spineIndex;

    protected AbstractSpinedBuffer() {
        this.initialChunkPower = 4;
    }

    protected AbstractSpinedBuffer(int n) {
        if (n >= 0) {
            this.initialChunkPower = Math.max(4, 32 - Integer.numberOfLeadingZeros(n - 1));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal Capacity: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    protected int chunkSize(int n) {
        n = n != 0 && n != 1 ? Math.min(this.initialChunkPower + n - 1, 30) : this.initialChunkPower;
        return 1 << n;
    }

    public abstract void clear();

    public long count() {
        int n = this.spineIndex;
        long l = n == 0 ? (long)this.elementIndex : this.priorElementCount[n] + (long)this.elementIndex;
        return l;
    }

    public boolean isEmpty() {
        boolean bl = this.spineIndex == 0 && this.elementIndex == 0;
        return bl;
    }
}

