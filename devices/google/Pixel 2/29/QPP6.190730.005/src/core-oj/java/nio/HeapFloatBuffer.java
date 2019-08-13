/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ReadOnlyBufferException;

class HeapFloatBuffer
extends FloatBuffer {
    HeapFloatBuffer(int n, int n2) {
        this(n, n2, false);
    }

    HeapFloatBuffer(int n, int n2, boolean bl) {
        super(-1, 0, n2, n, new float[n], 0);
        this.isReadOnly = bl;
    }

    HeapFloatBuffer(float[] arrf, int n, int n2) {
        this(arrf, n, n2, false);
    }

    protected HeapFloatBuffer(float[] arrf, int n, int n2, int n3, int n4, int n5) {
        this(arrf, n, n2, n3, n4, n5, false);
    }

    protected HeapFloatBuffer(float[] arrf, int n, int n2, int n3, int n4, int n5, boolean bl) {
        super(n, n2, n3, n4, arrf, n5);
        this.isReadOnly = bl;
    }

    HeapFloatBuffer(float[] arrf, int n, int n2, boolean bl) {
        super(-1, n, n + n2, arrf.length, arrf, 0);
        this.isReadOnly = bl;
    }

    @Override
    public FloatBuffer asReadOnlyBuffer() {
        return new HeapFloatBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, true);
    }

    @Override
    public FloatBuffer compact() {
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
    public FloatBuffer duplicate() {
        return new HeapFloatBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.isReadOnly);
    }

    @Override
    public float get() {
        return this.hb[this.ix(this.nextGetIndex())];
    }

    @Override
    public float get(int n) {
        return this.hb[this.ix(this.checkIndex(n))];
    }

    @Override
    public FloatBuffer get(float[] arrf, int n, int n2) {
        HeapFloatBuffer.checkBounds(n, n2, arrf.length);
        if (n2 <= this.remaining()) {
            System.arraycopy((Object)this.hb, this.ix(this.position()), (Object)arrf, n, n2);
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
    public FloatBuffer put(float f) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.nextPutIndex())] = f;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public FloatBuffer put(int n, float f) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.checkIndex((int)n))] = f;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public FloatBuffer put(FloatBuffer floatBuffer) {
        if (floatBuffer == this) throw new IllegalArgumentException();
        if (this.isReadOnly) throw new ReadOnlyBufferException();
        if (floatBuffer instanceof HeapFloatBuffer) {
            int n = (floatBuffer = (HeapFloatBuffer)floatBuffer).remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            System.arraycopy((Object)((HeapFloatBuffer)floatBuffer).hb, ((HeapFloatBuffer)floatBuffer).ix(floatBuffer.position()), (Object)this.hb, this.ix(this.position()), n);
            floatBuffer.position(floatBuffer.position() + n);
            this.position(this.position() + n);
            return this;
        } else if (floatBuffer.isDirect()) {
            int n = floatBuffer.remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            floatBuffer.get(this.hb, this.ix(this.position()), n);
            this.position(this.position() + n);
            return this;
        } else {
            super.put(floatBuffer);
        }
        return this;
    }

    @Override
    public FloatBuffer put(float[] arrf, int n, int n2) {
        if (!this.isReadOnly) {
            HeapFloatBuffer.checkBounds(n, n2, arrf.length);
            if (n2 <= this.remaining()) {
                System.arraycopy((Object)arrf, n, (Object)this.hb, this.ix(this.position()), n2);
                this.position(this.position() + n2);
                return this;
            }
            throw new BufferOverflowException();
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public FloatBuffer slice() {
        return new HeapFloatBuffer(this.hb, -1, 0, this.remaining(), this.remaining(), this.position() + this.offset, this.isReadOnly);
    }
}

