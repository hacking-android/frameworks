/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.Memory
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DirectByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ReadOnlyBufferException;
import libcore.io.Memory;

class ByteBufferAsFloatBuffer
extends FloatBuffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected final ByteBuffer bb;
    protected final int offset;
    private final ByteOrder order;

    ByteBufferAsFloatBuffer(ByteBuffer byteBuffer, int n, int n2, int n3, int n4, int n5, ByteOrder byteOrder) {
        super(n, n2, n3, n4);
        this.bb = byteBuffer.duplicate();
        this.isReadOnly = byteBuffer.isReadOnly;
        if (byteBuffer instanceof DirectByteBuffer) {
            this.address = byteBuffer.address + (long)n5;
        }
        this.bb.order(byteOrder);
        this.order = byteOrder;
        this.offset = n5;
    }

    @Override
    public FloatBuffer asReadOnlyBuffer() {
        return new ByteBufferAsFloatBuffer(this.bb.asReadOnlyBuffer(), this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.order);
    }

    @Override
    public FloatBuffer compact() {
        if (!this.isReadOnly) {
            int n;
            int n2 = this.position();
            n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
            ByteBuffer byteBuffer = this.bb;
            if (!(byteBuffer instanceof DirectByteBuffer)) {
                System.arraycopy(byteBuffer.array(), this.ix(n2), this.bb.array(), this.ix(0), n << 2);
            } else {
                Memory.memmove((Object)this, (int)this.ix(0), (Object)this, (int)this.ix(n2), (long)(n << 2));
            }
            this.position(n);
            this.limit(this.capacity());
            this.discardMark();
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public FloatBuffer duplicate() {
        return new ByteBufferAsFloatBuffer(this.bb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.order);
    }

    @Override
    public float get() {
        return this.get(this.nextGetIndex());
    }

    @Override
    public float get(int n) {
        return this.bb.getFloatUnchecked(this.ix(this.checkIndex(n)));
    }

    @Override
    public FloatBuffer get(float[] arrf, int n, int n2) {
        ByteBufferAsFloatBuffer.checkBounds(n, n2, arrf.length);
        if (n2 <= this.remaining()) {
            this.bb.getUnchecked(this.ix(this.position), arrf, n, n2);
            this.position += n2;
            return this;
        }
        throw new BufferUnderflowException();
    }

    @Override
    public boolean isDirect() {
        return this.bb.isDirect();
    }

    @Override
    public boolean isReadOnly() {
        return this.isReadOnly;
    }

    protected int ix(int n) {
        return (n << 2) + this.offset;
    }

    @Override
    public ByteOrder order() {
        return this.order;
    }

    @Override
    public FloatBuffer put(float f) {
        this.put(this.nextPutIndex(), f);
        return this;
    }

    @Override
    public FloatBuffer put(int n, float f) {
        if (!this.isReadOnly) {
            this.bb.putFloatUnchecked(this.ix(this.checkIndex(n)), f);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public FloatBuffer put(float[] arrf, int n, int n2) {
        ByteBufferAsFloatBuffer.checkBounds(n, n2, arrf.length);
        if (n2 <= this.remaining()) {
            this.bb.putUnchecked(this.ix(this.position), arrf, n, n2);
            this.position += n2;
            return this;
        }
        throw new BufferOverflowException();
    }

    @Override
    public FloatBuffer slice() {
        int n;
        int n2 = this.position();
        n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
        int n3 = this.offset;
        return new ByteBufferAsFloatBuffer(this.bb, -1, 0, n, n, (n2 << 2) + n3, this.order);
    }
}

