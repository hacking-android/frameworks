/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

class StringCharBuffer
extends CharBuffer {
    CharSequence str;

    StringCharBuffer(CharSequence charSequence, int n, int n2) {
        super(-1, n, n2, charSequence.length());
        int n3 = charSequence.length();
        if (n >= 0 && n <= n3 && n2 >= n && n2 <= n3) {
            this.str = charSequence;
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    private StringCharBuffer(CharSequence charSequence, int n, int n2, int n3, int n4, int n5) {
        super(n, n2, n3, n4, null, n5);
        this.str = charSequence;
    }

    @Override
    public CharBuffer asReadOnlyBuffer() {
        return this.duplicate();
    }

    @Override
    public final CharBuffer compact() {
        throw new ReadOnlyBufferException();
    }

    @Override
    public CharBuffer duplicate() {
        return new StringCharBuffer(this.str, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset);
    }

    @Override
    public final char get() {
        return this.str.charAt(this.nextGetIndex() + this.offset);
    }

    @Override
    public final char get(int n) {
        return this.str.charAt(this.checkIndex(n) + this.offset);
    }

    @Override
    char getUnchecked(int n) {
        return this.str.charAt(this.offset + n);
    }

    @Override
    public boolean isDirect() {
        return false;
    }

    @Override
    public final boolean isReadOnly() {
        return true;
    }

    @Override
    public ByteOrder order() {
        return ByteOrder.nativeOrder();
    }

    @Override
    public final CharBuffer put(char c) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public final CharBuffer put(int n, char c) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public CharBuffer slice() {
        return new StringCharBuffer(this.str, -1, 0, this.remaining(), this.remaining(), this.offset + this.position());
    }

    @Override
    public final CharBuffer subSequence(int n, int n2) {
        try {
            int n3 = this.position();
            StringCharBuffer stringCharBuffer = new StringCharBuffer(this.str, -1, n3 + this.checkIndex(n, n3), n3 + this.checkIndex(n2, n3), this.capacity(), this.offset);
            return stringCharBuffer;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    final String toString(int n, int n2) {
        return this.str.toString().substring(this.offset + n, this.offset + n2);
    }
}

