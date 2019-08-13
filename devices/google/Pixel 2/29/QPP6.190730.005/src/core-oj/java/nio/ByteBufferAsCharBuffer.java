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
import java.nio.CharBuffer;
import java.nio.DirectByteBuffer;
import java.nio.ReadOnlyBufferException;
import libcore.io.Memory;

class ByteBufferAsCharBuffer
extends CharBuffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected final ByteBuffer bb;
    protected final int offset;
    private final ByteOrder order;

    ByteBufferAsCharBuffer(ByteBuffer byteBuffer, int n, int n2, int n3, int n4, int n5, ByteOrder byteOrder) {
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
    public CharBuffer asReadOnlyBuffer() {
        return new ByteBufferAsCharBuffer(this.bb.asReadOnlyBuffer(), this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.order);
    }

    @Override
    public CharBuffer compact() {
        if (!this.isReadOnly) {
            int n;
            int n2 = this.position();
            n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
            ByteBuffer byteBuffer = this.bb;
            if (!(byteBuffer instanceof DirectByteBuffer)) {
                System.arraycopy(byteBuffer.array(), this.ix(n2), this.bb.array(), this.ix(0), n << 1);
            } else {
                Memory.memmove((Object)this, (int)this.ix(0), (Object)this, (int)this.ix(n2), (long)(n << 1));
            }
            this.position(n);
            this.limit(this.capacity());
            this.discardMark();
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public CharBuffer duplicate() {
        return new ByteBufferAsCharBuffer(this.bb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.order);
    }

    @Override
    public char get() {
        return this.get(this.nextGetIndex());
    }

    @Override
    public char get(int n) {
        return this.bb.getCharUnchecked(this.ix(this.checkIndex(n)));
    }

    @Override
    public CharBuffer get(char[] arrc, int n, int n2) {
        ByteBufferAsCharBuffer.checkBounds(n, n2, arrc.length);
        if (n2 <= this.remaining()) {
            this.bb.getUnchecked(this.ix(this.position), arrc, n, n2);
            this.position += n2;
            return this;
        }
        throw new BufferUnderflowException();
    }

    @Override
    char getUnchecked(int n) {
        return this.bb.getCharUnchecked(this.ix(n));
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
        return (n << 1) + this.offset;
    }

    @Override
    public ByteOrder order() {
        return this.order;
    }

    @Override
    public CharBuffer put(char c) {
        this.put(this.nextPutIndex(), c);
        return this;
    }

    @Override
    public CharBuffer put(int n, char c) {
        if (!this.isReadOnly) {
            this.bb.putCharUnchecked(this.ix(this.checkIndex(n)), c);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public CharBuffer put(char[] arrc, int n, int n2) {
        ByteBufferAsCharBuffer.checkBounds(n, n2, arrc.length);
        if (n2 <= this.remaining()) {
            this.bb.putUnchecked(this.ix(this.position), arrc, n, n2);
            this.position += n2;
            return this;
        }
        throw new BufferOverflowException();
    }

    @Override
    public CharBuffer slice() {
        int n;
        int n2 = this.position();
        n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
        int n3 = this.offset;
        return new ByteBufferAsCharBuffer(this.bb, -1, 0, n, n, (n2 << 1) + n3, this.order);
    }

    @Override
    public CharBuffer subSequence(int n, int n2) {
        int n3;
        int n4 = this.position();
        if (n4 > (n3 = this.limit())) {
            n4 = n3;
        }
        if (n >= 0 && n2 <= n3 - n4 && n <= n2) {
            return new ByteBufferAsCharBuffer(this.bb, -1, n4 + n, n4 + n2, this.capacity(), this.offset, this.order);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String toString(int n, int n2) {
        if (n2 <= this.limit() && n <= n2) {
            try {
                char[] arrc = new char[n2 - n];
                CharBuffer charBuffer = CharBuffer.wrap(arrc);
                CharSequence charSequence = this.duplicate();
                charSequence.position(n);
                charSequence.limit(n2);
                charBuffer.put((CharBuffer)charSequence);
                charSequence = new String(arrc);
                return charSequence;
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                throw new IndexOutOfBoundsException();
            }
        }
        throw new IndexOutOfBoundsException();
    }
}

