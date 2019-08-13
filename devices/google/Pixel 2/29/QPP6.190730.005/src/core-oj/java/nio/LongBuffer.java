/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.HeapLongBuffer;
import java.nio.ReadOnlyBufferException;

public abstract class LongBuffer
extends Buffer
implements Comparable<LongBuffer> {
    final long[] hb;
    boolean isReadOnly;
    final int offset;

    LongBuffer(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, null, 0);
    }

    LongBuffer(int n, int n2, int n3, int n4, long[] arrl, int n5) {
        super(n, n2, n3, n4, 3);
        this.hb = arrl;
        this.offset = n5;
    }

    public static LongBuffer allocate(int n) {
        if (n >= 0) {
            return new HeapLongBuffer(n, n);
        }
        throw new IllegalArgumentException();
    }

    private static int compare(long l, long l2) {
        return Long.compare(l, l2);
    }

    private static boolean equals(long l, long l2) {
        boolean bl = l == l2;
        return bl;
    }

    public static LongBuffer wrap(long[] arrl) {
        return LongBuffer.wrap(arrl, 0, arrl.length);
    }

    public static LongBuffer wrap(long[] object, int n, int n2) {
        try {
            object = new HeapLongBuffer((long[])object, n, n2);
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException();
        }
    }

    public final long[] array() {
        long[] arrl = this.hb;
        if (arrl != null) {
            if (!this.isReadOnly) {
                return arrl;
            }
            throw new ReadOnlyBufferException();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public final int arrayOffset() {
        if (this.hb != null) {
            if (!this.isReadOnly) {
                return this.offset;
            }
            throw new ReadOnlyBufferException();
        }
        throw new UnsupportedOperationException();
    }

    public abstract LongBuffer asReadOnlyBuffer();

    @Override
    public Buffer clear() {
        return super.clear();
    }

    public abstract LongBuffer compact();

    @Override
    public int compareTo(LongBuffer longBuffer) {
        int n = this.position();
        int n2 = Math.min(this.remaining(), longBuffer.remaining());
        int n3 = this.position();
        int n4 = longBuffer.position();
        while (n3 < n + n2) {
            int n5 = LongBuffer.compare(this.get(n3), longBuffer.get(n4));
            if (n5 != 0) {
                return n5;
            }
            ++n3;
            ++n4;
        }
        return this.remaining() - longBuffer.remaining();
    }

    public abstract LongBuffer duplicate();

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LongBuffer)) {
            return false;
        }
        object = (LongBuffer)object;
        if (this.remaining() != ((Buffer)object).remaining()) {
            return false;
        }
        int n = this.position();
        int n2 = this.limit() - 1;
        int n3 = ((Buffer)object).limit() - 1;
        while (n2 >= n) {
            if (!LongBuffer.equals(this.get(n2), ((LongBuffer)object).get(n3))) {
                return false;
            }
            --n2;
            --n3;
        }
        return true;
    }

    @Override
    public Buffer flip() {
        return super.flip();
    }

    public abstract long get();

    public abstract long get(int var1);

    public LongBuffer get(long[] arrl) {
        return this.get(arrl, 0, arrl.length);
    }

    public LongBuffer get(long[] arrl, int n, int n2) {
        LongBuffer.checkBounds(n, n2, arrl.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                arrl[i] = this.get();
            }
            return this;
        }
        throw new BufferUnderflowException();
    }

    @Override
    public final boolean hasArray() {
        boolean bl = this.hb != null && !this.isReadOnly;
        return bl;
    }

    public int hashCode() {
        int n = 1;
        int n2 = this.position();
        for (int i = this.limit() - 1; i >= n2; --i) {
            n = n * 31 + (int)this.get(i);
        }
        return n;
    }

    @Override
    public abstract boolean isDirect();

    @Override
    public Buffer limit(int n) {
        return super.limit(n);
    }

    @Override
    public Buffer mark() {
        return super.mark();
    }

    public abstract ByteOrder order();

    @Override
    public Buffer position(int n) {
        return super.position(n);
    }

    public abstract LongBuffer put(int var1, long var2);

    public abstract LongBuffer put(long var1);

    public LongBuffer put(LongBuffer longBuffer) {
        if (longBuffer != this) {
            if (!this.isReadOnly()) {
                int n = longBuffer.remaining();
                if (n <= this.remaining()) {
                    for (int i = 0; i < n; ++i) {
                        this.put(longBuffer.get());
                    }
                    return this;
                }
                throw new BufferOverflowException();
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalArgumentException();
    }

    public final LongBuffer put(long[] arrl) {
        return this.put(arrl, 0, arrl.length);
    }

    public LongBuffer put(long[] arrl, int n, int n2) {
        LongBuffer.checkBounds(n, n2, arrl.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                this.put(arrl[i]);
            }
            return this;
        }
        throw new BufferOverflowException();
    }

    @Override
    public Buffer reset() {
        return super.reset();
    }

    @Override
    public Buffer rewind() {
        return super.rewind();
    }

    public abstract LongBuffer slice();

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getClass().getName());
        stringBuffer.append("[pos=");
        stringBuffer.append(this.position());
        stringBuffer.append(" lim=");
        stringBuffer.append(this.limit());
        stringBuffer.append(" cap=");
        stringBuffer.append(this.capacity());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

