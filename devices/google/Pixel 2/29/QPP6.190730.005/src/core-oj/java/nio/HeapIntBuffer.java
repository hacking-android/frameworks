/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ReadOnlyBufferException;

class HeapIntBuffer
extends IntBuffer {
    HeapIntBuffer(int n, int n2) {
        this(n, n2, false);
    }

    HeapIntBuffer(int n, int n2, boolean bl) {
        super(-1, 0, n2, n, new int[n], 0);
        this.isReadOnly = bl;
    }

    HeapIntBuffer(int[] arrn, int n, int n2) {
        this(arrn, n, n2, false);
    }

    protected HeapIntBuffer(int[] arrn, int n, int n2, int n3, int n4, int n5) {
        this(arrn, n, n2, n3, n4, n5, false);
    }

    protected HeapIntBuffer(int[] arrn, int n, int n2, int n3, int n4, int n5, boolean bl) {
        super(n, n2, n3, n4, arrn, n5);
        this.isReadOnly = bl;
    }

    HeapIntBuffer(int[] arrn, int n, int n2, boolean bl) {
        super(-1, n, n + n2, arrn.length, arrn, 0);
        this.isReadOnly = bl;
    }

    @Override
    public IntBuffer asReadOnlyBuffer() {
        return new HeapIntBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, true);
    }

    @Override
    public IntBuffer compact() {
        if (!this.isReadOnly) {
            System.arraycopy((Object)this.hb, this.ix(this.position()), (Object)this.hb, this.ix(0), this.remaining());
            this.position(this.remaining());
            this.limit(this.capacity());
            this.discardMark();
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public IntBuffer duplicate() {
        return new HeapIntBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.isReadOnly);
    }

    @Override
    public int get() {
        return this.hb[this.ix(this.nextGetIndex())];
    }

    @Override
    public int get(int n) {
        return this.hb[this.ix(this.checkIndex(n))];
    }

    @Override
    public IntBuffer get(int[] arrn, int n, int n2) {
        HeapIntBuffer.checkBounds(n, n2, arrn.length);
        if (n2 <= this.remaining()) {
            System.arraycopy((Object)this.hb, this.ix(this.position()), (Object)arrn, n, n2);
            this.position(this.position() + n2);
            return this;
        }
        throw new BufferUnderflowException();
    }

    @Override
    public boolean isDirect() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return this.isReadOnly;
    }

    protected int ix(int n) {
        return this.offset + n;
    }

    @Override
    public ByteOrder order() {
        return ByteOrder.nativeOrder();
    }

    @Override
    public IntBuffer put(int n) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.nextPutIndex())] = n;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public IntBuffer put(int n, int n2) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.checkIndex((int)n))] = n2;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public IntBuffer put(IntBuffer intBuffer) {
        if (intBuffer == this) throw new IllegalArgumentException();
        if (this.isReadOnly) throw new ReadOnlyBufferException();
        if (intBuffer instanceof HeapIntBuffer) {
            int n = (intBuffer = (HeapIntBuffer)intBuffer).remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            System.arraycopy((Object)((HeapIntBuffer)intBuffer).hb, ((HeapIntBuffer)intBuffer).ix(intBuffer.position()), (Object)this.hb, this.ix(this.position()), n);
            intBuffer.position(intBuffer.position() + n);
            this.position(this.position() + n);
            return this;
        } else if (intBuffer.isDirect()) {
            int n = intBuffer.remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            intBuffer.get(this.hb, this.ix(this.position()), n);
            this.position(this.position() + n);
            return this;
        } else {
            super.put(intBuffer);
        }
        return this;
    }

    @Override
    public IntBuffer put(int[] arrn, int n, int n2) {
        if (!this.isReadOnly) {
            HeapIntBuffer.checkBounds(n, n2, arrn.length);
            if (n2 <= this.remaining()) {
                System.arraycopy((Object)arrn, n, (Object)this.hb, this.ix(this.position()), n2);
                this.position(this.position() + n2);
                return this;
            }
            throw new BufferOverflowException();
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public IntBuffer slice() {
        return new HeapIntBuffer(this.hb, -1, 0, this.remaining(), this.remaining(), this.position() + this.offset, this.isReadOnly);
    }
}

