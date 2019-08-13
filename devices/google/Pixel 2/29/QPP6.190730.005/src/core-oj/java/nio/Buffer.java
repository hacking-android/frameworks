/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.InvalidMarkException;

public abstract class Buffer {
    static final int SPLITERATOR_CHARACTERISTICS = 16464;
    final int _elementSizeShift;
    long address;
    private int capacity;
    private int limit;
    private int mark = -1;
    int position = 0;

    Buffer(int n, int n2, int n3, int n4, int n5) {
        if (n4 >= 0) {
            this.capacity = n4;
            this.limit(n3);
            this.position(n2);
            if (n >= 0) {
                if (n <= n2) {
                    this.mark = n;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("mark > position: (");
                    stringBuilder.append(n);
                    stringBuilder.append(" > ");
                    stringBuilder.append(n2);
                    stringBuilder.append(")");
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            this._elementSizeShift = n5;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Negative capacity: ");
        stringBuilder.append(n4);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static void checkBounds(int n, int n2, int n3) {
        if ((n | n2 | n + n2 | n3 - (n + n2)) >= 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("off=");
        stringBuilder.append(n);
        stringBuilder.append(", len=");
        stringBuilder.append(n2);
        stringBuilder.append(" out of bounds (size=");
        stringBuilder.append(n3);
        stringBuilder.append(")");
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public abstract Object array();

    public abstract int arrayOffset();

    public final int capacity() {
        return this.capacity;
    }

    final int checkIndex(int n) {
        if (n >= 0 && n < this.limit) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("index=");
        stringBuilder.append(n);
        stringBuilder.append(" out of bounds (limit=");
        stringBuilder.append(this.limit);
        stringBuilder.append(")");
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    final int checkIndex(int n, int n2) {
        if (n >= 0 && n2 <= this.limit - n) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("index=");
        stringBuilder.append(n);
        stringBuilder.append(" out of bounds (limit=");
        stringBuilder.append(this.limit);
        stringBuilder.append(", nb=");
        stringBuilder.append(n2);
        stringBuilder.append(")");
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public Buffer clear() {
        this.position = 0;
        this.limit = this.capacity;
        this.mark = -1;
        return this;
    }

    final void discardMark() {
        this.mark = -1;
    }

    public Buffer flip() {
        this.limit = this.position;
        this.position = 0;
        this.mark = -1;
        return this;
    }

    public int getElementSizeShift() {
        return this._elementSizeShift;
    }

    public abstract boolean hasArray();

    public final boolean hasRemaining() {
        boolean bl = this.position < this.limit;
        return bl;
    }

    public abstract boolean isDirect();

    public abstract boolean isReadOnly();

    public final int limit() {
        return this.limit;
    }

    public Buffer limit(int n) {
        if (n <= this.capacity && n >= 0) {
            this.limit = n;
            n = this.position;
            int n2 = this.limit;
            if (n > n2) {
                this.position = n2;
            }
            if (this.mark > this.limit) {
                this.mark = -1;
            }
            return this;
        }
        throw new IllegalArgumentException();
    }

    public Buffer mark() {
        this.mark = this.position;
        return this;
    }

    final int markValue() {
        return this.mark;
    }

    final int nextGetIndex() {
        int n = this.position;
        if (n < this.limit) {
            this.position = n + 1;
            return n;
        }
        throw new BufferUnderflowException();
    }

    final int nextGetIndex(int n) {
        int n2 = this.limit;
        int n3 = this.position;
        if (n2 - n3 >= n) {
            n2 = this.position;
            this.position = n3 + n;
            return n2;
        }
        throw new BufferUnderflowException();
    }

    final int nextPutIndex() {
        int n = this.position;
        if (n < this.limit) {
            this.position = n + 1;
            return n;
        }
        throw new BufferOverflowException();
    }

    final int nextPutIndex(int n) {
        int n2 = this.limit;
        int n3 = this.position;
        if (n2 - n3 >= n) {
            n2 = this.position;
            this.position = n3 + n;
            return n2;
        }
        throw new BufferOverflowException();
    }

    public final int position() {
        return this.position;
    }

    public Buffer position(int n) {
        if (n <= this.limit && n >= 0) {
            this.position = n;
            if (this.mark > this.position) {
                this.mark = -1;
            }
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad position ");
        stringBuilder.append(n);
        stringBuilder.append("/");
        stringBuilder.append(this.limit);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final int remaining() {
        return this.limit - this.position;
    }

    public Buffer reset() {
        int n = this.mark;
        if (n >= 0) {
            this.position = n;
            return this;
        }
        throw new InvalidMarkException();
    }

    public Buffer rewind() {
        this.position = 0;
        this.mark = -1;
        return this;
    }

    final void truncate() {
        this.mark = -1;
        this.position = 0;
        this.limit = 0;
        this.capacity = 0;
    }
}

