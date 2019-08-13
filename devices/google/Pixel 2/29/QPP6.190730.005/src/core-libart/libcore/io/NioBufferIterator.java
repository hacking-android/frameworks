/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import libcore.io.BufferIterator;
import libcore.io.Memory;
import libcore.io.MemoryMappedFile;

public final class NioBufferIterator
extends BufferIterator {
    private final long address;
    private final MemoryMappedFile file;
    private final int length;
    private int position;
    private final boolean swap;

    NioBufferIterator(MemoryMappedFile object, long l, int n, boolean bl) {
        ((MemoryMappedFile)object).checkNotClosed();
        this.file = object;
        this.address = l;
        if (n >= 0) {
            if (Long.compareUnsigned(l, -1L - (long)n) <= 0) {
                this.length = n;
                this.swap = bl;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("length ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" would overflow 64-bit address space");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("length < 0");
    }

    private static void checkDstBounds(int n, int n2, int n3) {
        if (n >= 0 && n3 >= 0) {
            int n4 = n + n3;
            if (n4 >= 0 && n4 <= n2) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Write outside range: dst.length=");
            stringBuilder.append(n2);
            stringBuilder.append(", offset=");
            stringBuilder.append(n);
            stringBuilder.append(", count=");
            stringBuilder.append(n3);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid dst args: offset=");
        stringBuilder.append(n2);
        stringBuilder.append(", count=");
        stringBuilder.append(n3);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private static void checkReadBounds(int n, int n2, int n3) {
        if (n >= 0 && n3 >= 0) {
            int n4 = n + n3;
            if (n4 >= 0 && n4 <= n2) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Read outside range: position=");
            stringBuilder.append(n);
            stringBuilder.append(", byteCount=");
            stringBuilder.append(n3);
            stringBuilder.append(", length=");
            stringBuilder.append(n2);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid read args: position=");
        stringBuilder.append(n);
        stringBuilder.append(", byteCount=");
        stringBuilder.append(n3);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    @Override
    public int pos() {
        return this.position;
    }

    @Override
    public byte readByte() {
        this.file.checkNotClosed();
        NioBufferIterator.checkReadBounds(this.position, this.length, 1);
        byte by = Memory.peekByte(this.address + (long)this.position);
        ++this.position;
        return by;
    }

    @Override
    public void readByteArray(byte[] arrby, int n, int n2) {
        NioBufferIterator.checkDstBounds(n, arrby.length, n2);
        this.file.checkNotClosed();
        NioBufferIterator.checkReadBounds(this.position, this.length, n2);
        Memory.peekByteArray(this.address + (long)this.position, arrby, n, n2);
        this.position += n2;
    }

    @Override
    public int readInt() {
        this.file.checkNotClosed();
        NioBufferIterator.checkReadBounds(this.position, this.length, 4);
        int n = Memory.peekInt(this.address + (long)this.position, this.swap);
        this.position += 4;
        return n;
    }

    @Override
    public void readIntArray(int[] arrn, int n, int n2) {
        NioBufferIterator.checkDstBounds(n, arrn.length, n2);
        this.file.checkNotClosed();
        int n3 = n2 * 4;
        NioBufferIterator.checkReadBounds(this.position, this.length, n3);
        Memory.peekIntArray(this.address + (long)this.position, arrn, n, n2, this.swap);
        this.position += n3;
    }

    @Override
    public short readShort() {
        this.file.checkNotClosed();
        NioBufferIterator.checkReadBounds(this.position, this.length, 2);
        short s = Memory.peekShort(this.address + (long)this.position, this.swap);
        this.position += 2;
        return s;
    }

    @Override
    public void seek(int n) {
        this.position = n;
    }

    @Override
    public void skip(int n) {
        this.position += n;
    }
}

