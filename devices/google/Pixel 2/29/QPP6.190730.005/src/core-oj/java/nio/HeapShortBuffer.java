/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.ShortBuffer;

class HeapShortBuffer
extends ShortBuffer {
    HeapShortBuffer(int n, int n2) {
        this(n, n2, false);
    }

    HeapShortBuffer(int n, int n2, boolean bl) {
        super(-1, 0, n2, n, new short[n], 0);
        this.isReadOnly = bl;
    }

    HeapShortBuffer(short[] arrs, int n, int n2) {
        this(arrs, n, n2, false);
    }

    protected HeapShortBuffer(short[] arrs, int n, int n2, int n3, int n4, int n5) {
        this(arrs, n, n2, n3, n4, n5, false);
    }

    protected HeapShortBuffer(short[] arrs, int n, int n2, int n3, int n4, int n5, boolean bl) {
        super(n, n2, n3, n4, arrs, n5);
        this.isReadOnly = bl;
    }

    HeapShortBuffer(short[] arrs, int n, int n2, boolean bl) {
        super(-1, n, n + n2, arrs.length, arrs, 0);
        this.isReadOnly = bl;
    }

    @Override
    public ShortBuffer asReadOnlyBuffer() {
        return new HeapShortBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, true);
    }

    @Override
    public ShortBuffer compact() {
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
    public ShortBuffer duplicate() {
        return new HeapShortBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.isReadOnly);
    }

    @Override
    public ShortBuffer get(short[] arrs, int n, int n2) {
        HeapShortBuffer.checkBounds(n, n2, arrs.length);
        if (n2 <= this.remaining()) {
            System.arraycopy((Object)this.hb, this.ix(this.position()), (Object)arrs, n, n2);
            this.position(this.position() + n2);
            return this;
        }
        throw new BufferUnderflowException();
    }

    @Override
    public short get() {
        return this.hb[this.ix(this.nextGetIndex())];
    }

    @Override
    public short get(int n) {
        return this.hb[this.ix(this.checkIndex(n))];
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
    public ShortBuffer put(int n, short s) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.checkIndex((int)n))] = s;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public ShortBuffer put(ShortBuffer shortBuffer) {
        if (shortBuffer == this) throw new IllegalArgumentException();
        if (this.isReadOnly) throw new ReadOnlyBufferException();
        if (shortBuffer instanceof HeapShortBuffer) {
            int n = (shortBuffer = (HeapShortBuffer)shortBuffer).remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            System.arraycopy((Object)((HeapShortBuffer)shortBuffer).hb, ((HeapShortBuffer)shortBuffer).ix(shortBuffer.position()), (Object)this.hb, this.ix(this.position()), n);
            shortBuffer.position(shortBuffer.position() + n);
            this.position(this.position() + n);
            return this;
        } else if (shortBuffer.isDirect()) {
            int n = shortBuffer.remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            shortBuffer.get(this.hb, this.ix(this.position()), n);
            this.position(this.position() + n);
            return this;
        } else {
            super.put(shortBuffer);
        }
        return this;
    }

    @Override
    public ShortBuffer put(short s) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.nextPutIndex())] = s;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ShortBuffer put(short[] arrs, int n, int n2) {
        if (!this.isReadOnly) {
            HeapShortBuffer.checkBounds(n, n2, arrs.length);
            if (n2 <= this.remaining()) {
                System.arraycopy((Object)arrs, n, (Object)this.hb, this.ix(this.position()), n2);
                this.position(this.position() + n2);
                return this;
            }
            throw new BufferOverflowException();
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ShortBuffer slice() {
        return new HeapShortBuffer(this.hb, -1, 0, this.remaining(), this.remaining(), this.position() + this.offset, this.isReadOnly);
    }
}

