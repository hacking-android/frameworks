/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.ReadOnlyBufferException;

class HeapDoubleBuffer
extends DoubleBuffer {
    HeapDoubleBuffer(int n, int n2) {
        this(n, n2, false);
    }

    HeapDoubleBuffer(int n, int n2, boolean bl) {
        super(-1, 0, n2, n, new double[n], 0);
        this.isReadOnly = bl;
    }

    HeapDoubleBuffer(double[] arrd, int n, int n2) {
        this(arrd, n, n2, false);
    }

    protected HeapDoubleBuffer(double[] arrd, int n, int n2, int n3, int n4, int n5) {
        this(arrd, n, n2, n3, n4, n5, false);
    }

    protected HeapDoubleBuffer(double[] arrd, int n, int n2, int n3, int n4, int n5, boolean bl) {
        super(n, n2, n3, n4, arrd, n5);
        this.isReadOnly = bl;
    }

    HeapDoubleBuffer(double[] arrd, int n, int n2, boolean bl) {
        super(-1, n, n + n2, arrd.length, arrd, 0);
        this.isReadOnly = bl;
    }

    @Override
    public DoubleBuffer asReadOnlyBuffer() {
        return new HeapDoubleBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, true);
    }

    @Override
    public DoubleBuffer compact() {
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
    public DoubleBuffer duplicate() {
        return new HeapDoubleBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.isReadOnly);
    }

    @Override
    public double get() {
        return this.hb[this.ix(this.nextGetIndex())];
    }

    @Override
    public double get(int n) {
        return this.hb[this.ix(this.checkIndex(n))];
    }

    @Override
    public DoubleBuffer get(double[] arrd, int n, int n2) {
        HeapDoubleBuffer.checkBounds(n, n2, arrd.length);
        if (n2 <= this.remaining()) {
            System.arraycopy((Object)this.hb, this.ix(this.position()), (Object)arrd, n, n2);
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
    public DoubleBuffer put(double d) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.nextPutIndex())] = d;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public DoubleBuffer put(int n, double d) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.checkIndex((int)n))] = d;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public DoubleBuffer put(DoubleBuffer doubleBuffer) {
        if (doubleBuffer == this) throw new IllegalArgumentException();
        if (this.isReadOnly) throw new ReadOnlyBufferException();
        if (doubleBuffer instanceof HeapDoubleBuffer) {
            int n = (doubleBuffer = (HeapDoubleBuffer)doubleBuffer).remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            System.arraycopy((Object)((HeapDoubleBuffer)doubleBuffer).hb, ((HeapDoubleBuffer)doubleBuffer).ix(doubleBuffer.position()), (Object)this.hb, this.ix(this.position()), n);
            doubleBuffer.position(doubleBuffer.position() + n);
            this.position(this.position() + n);
            return this;
        } else if (doubleBuffer.isDirect()) {
            int n = doubleBuffer.remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            doubleBuffer.get(this.hb, this.ix(this.position()), n);
            this.position(this.position() + n);
            return this;
        } else {
            super.put(doubleBuffer);
        }
        return this;
    }

    @Override
    public DoubleBuffer put(double[] arrd, int n, int n2) {
        if (!this.isReadOnly) {
            HeapDoubleBuffer.checkBounds(n, n2, arrd.length);
            if (n2 <= this.remaining()) {
                System.arraycopy((Object)arrd, n, (Object)this.hb, this.ix(this.position()), n2);
                this.position(this.position() + n2);
                return this;
            }
            throw new BufferOverflowException();
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public DoubleBuffer slice() {
        return new HeapDoubleBuffer(this.hb, -1, 0, this.remaining(), this.remaining(), this.position() + this.offset, this.isReadOnly);
    }
}

