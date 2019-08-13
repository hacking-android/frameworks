/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

class HeapCharBuffer
extends CharBuffer {
    HeapCharBuffer(int n, int n2) {
        this(n, n2, false);
    }

    HeapCharBuffer(int n, int n2, boolean bl) {
        super(-1, 0, n2, n, new char[n], 0);
        this.isReadOnly = bl;
    }

    HeapCharBuffer(char[] arrc, int n, int n2) {
        this(arrc, n, n2, false);
    }

    protected HeapCharBuffer(char[] arrc, int n, int n2, int n3, int n4, int n5) {
        this(arrc, n, n2, n3, n4, n5, false);
    }

    protected HeapCharBuffer(char[] arrc, int n, int n2, int n3, int n4, int n5, boolean bl) {
        super(n, n2, n3, n4, arrc, n5);
        this.isReadOnly = bl;
    }

    HeapCharBuffer(char[] arrc, int n, int n2, boolean bl) {
        super(-1, n, n + n2, arrc.length, arrc, 0);
        this.isReadOnly = bl;
    }

    @Override
    public CharBuffer asReadOnlyBuffer() {
        return new HeapCharBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, true);
    }

    @Override
    public CharBuffer compact() {
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
    public CharBuffer duplicate() {
        return new HeapCharBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.isReadOnly);
    }

    @Override
    public char get() {
        return this.hb[this.ix(this.nextGetIndex())];
    }

    @Override
    public char get(int n) {
        return this.hb[this.ix(this.checkIndex(n))];
    }

    @Override
    public CharBuffer get(char[] arrc, int n, int n2) {
        HeapCharBuffer.checkBounds(n, n2, arrc.length);
        if (n2 <= this.remaining()) {
            System.arraycopy((Object)this.hb, this.ix(this.position()), (Object)arrc, n, n2);
            this.position(this.position() + n2);
            return this;
        }
        throw new BufferUnderflowException();
    }

    @Override
    char getUnchecked(int n) {
        return this.hb[this.ix(n)];
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
    public CharBuffer put(char c) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.nextPutIndex())] = c;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public CharBuffer put(int n, char c) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.checkIndex((int)n))] = c;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public CharBuffer put(CharBuffer charBuffer) {
        if (charBuffer == this) throw new IllegalArgumentException();
        if (this.isReadOnly) throw new ReadOnlyBufferException();
        if (charBuffer instanceof HeapCharBuffer) {
            int n = (charBuffer = (HeapCharBuffer)charBuffer).remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            System.arraycopy((Object)((HeapCharBuffer)charBuffer).hb, ((HeapCharBuffer)charBuffer).ix(charBuffer.position()), (Object)this.hb, this.ix(this.position()), n);
            charBuffer.position(charBuffer.position() + n);
            this.position(this.position() + n);
            return this;
        } else if (charBuffer.isDirect()) {
            int n = charBuffer.remaining();
            if (n > this.remaining()) throw new BufferOverflowException();
            charBuffer.get(this.hb, this.ix(this.position()), n);
            this.position(this.position() + n);
            return this;
        } else {
            super.put(charBuffer);
        }
        return this;
    }

    @Override
    public CharBuffer put(char[] arrc, int n, int n2) {
        if (!this.isReadOnly) {
            HeapCharBuffer.checkBounds(n, n2, arrc.length);
            if (n2 <= this.remaining()) {
                System.arraycopy((Object)arrc, n, (Object)this.hb, this.ix(this.position()), n2);
                this.position(this.position() + n2);
                return this;
            }
            throw new BufferOverflowException();
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public CharBuffer slice() {
        return new HeapCharBuffer(this.hb, -1, 0, this.remaining(), this.remaining(), this.position() + this.offset, this.isReadOnly);
    }

    @Override
    public CharBuffer subSequence(int n, int n2) {
        if (n >= 0 && n2 <= this.length() && n <= n2) {
            int n3 = this.position();
            return new HeapCharBuffer(this.hb, -1, n3 + n, n3 + n2, this.capacity(), this.offset, this.isReadOnly);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    String toString(int n, int n2) {
        try {
            String string = new String(this.hb, this.offset + n, n2 - n);
            return string;
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            throw new IndexOutOfBoundsException();
        }
    }
}

