/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.nio.ReadOnlyBufferException;

class HeapLongBuffer
extends LongBuffer {
    HeapLongBuffer(int n, int n2) {
        this(n, n2, false);
    }

    HeapLongBuffer(int n, int n2, boolean bl) {
        super(-1, 0, n2, n, new long[n], 0);
        this.isReadOnly = bl;
    }

    HeapLongBuffer(long[] arrl, int n, int n2) {
        this(arrl, n, n2, false);
    }

    protected HeapLongBuffer(long[] arrl, int n, int n2, int n3, int n4, int n5) {
        this(arrl, n, n2, n3, n4, n5, false);
    }

    protected HeapLongBuffer(long[] arrl, int n, int n2, int n3, int n4, int n5, boolean bl) {
        super(n, n2, n3, n4, arrl, n5);
        this.isReadOnly = bl;
    }

    HeapLongBuffer(long[] arrl, int n, int n2, boolean bl) {
        super(-1, n, n + n2, arrl.length, arrl, 0);
        this.isReadOnly = bl;
    }

    @Override
    public LongBuffer asReadOnlyBuffer() {
        return new HeapLongBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, true);
    }

    @Override
    public LongBuffer compact() {
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
    public LongBuffer duplicate() {
        return new HeapLongBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.isReadOnly);
    }

    @Override
    public long get() {
        return this.hb[this.ix(this.nextGetIndex())];
    }

    @Override
    public long get(int n) {
        return this.hb[this.ix(this.checkIndex(n))];
    }

    @Override
    public LongBuffer get(long[] arrl, int n, int n2) {
        HeapLongBuffer.checkBounds(n, n2, arrl.length);
        if (n2 <= this.remaining()) {
            System.arraycopy((Object)this.hb, this.ix(this.position()), (Object)arrl, n, n2);
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
    public LongBuffer put(int n, long l) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.checkIndex((int)n))] = l;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public LongBuffer put(long l) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.nextPutIndex())] = l;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public LongBuffer put(LongBuffer longBuffer) {
        if (longBuffer == this) throw new IllegalArgumentException();
        if (this.isReadOnly) throw new ReadOnlyBufferException();
        if (longBuffer instanceof HeapLongBuffer) {
            int n = (longBuffer = (HeapLongBuffer)longBuffer).remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            System.arraycopy((Object)((HeapLongBuffer)longBuffer).hb, ((HeapLongBuffer)longBuffer).ix(longBuffer.position()), (Object)this.hb, this.ix(this.position()), n);
            longBuffer.position(longBuffer.position() + n);
            this.position(this.position() + n);
            return this;
        } else if (longBuffer.isDirect()) {
            int n = longBuffer.remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            longBuffer.get(this.hb, this.ix(this.position()), n);
            this.position(this.position() + n);
            return this;
        } else {
            super.put(longBuffer);
        }
        return this;
    }

    @Override
    public LongBuffer put(long[] arrl, int n, int n2) {
        if (!this.isReadOnly) {
            HeapLongBuffer.checkBounds(n, n2, arrl.length);
            if (n2 <= this.remaining()) {
                System.arraycopy((Object)arrl, n, (Object)this.hb, this.ix(this.position()), n2);
                this.position(this.position() + n2);
                return this;
            }
            throw new BufferOverflowException();
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public LongBuffer slice() {
        return new HeapLongBuffer(this.hb, -1, 0, this.remaining(), this.remaining(), this.position() + this.offset, this.isReadOnly);
    }
}

