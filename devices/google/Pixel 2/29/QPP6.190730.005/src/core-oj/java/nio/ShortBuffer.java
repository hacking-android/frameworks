/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.HeapShortBuffer;
import java.nio.ReadOnlyBufferException;

public abstract class ShortBuffer
extends Buffer
implements Comparable<ShortBuffer> {
    final short[] hb;
    boolean isReadOnly;
    final int offset;

    ShortBuffer(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, null, 0);
    }

    ShortBuffer(int n, int n2, int n3, int n4, short[] arrs, int n5) {
        super(n, n2, n3, n4, 1);
        this.hb = arrs;
        this.offset = n5;
    }

    public static ShortBuffer allocate(int n) {
        if (n >= 0) {
            return new HeapShortBuffer(n, n);
        }
        throw new IllegalArgumentException();
    }

    private static int compare(short s, short s2) {
        return Short.compare(s, s2);
    }

    private static boolean equals(short s, short s2) {
        boolean bl = s == s2;
        return bl;
    }

    public static ShortBuffer wrap(short[] arrs) {
        return ShortBuffer.wrap(arrs, 0, arrs.length);
    }

    public static ShortBuffer wrap(short[] object, int n, int n2) {
        try {
            object = new HeapShortBuffer((short[])object, n, n2);
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException();
        }
    }

    public final short[] array() {
        short[] arrs = this.hb;
        if (arrs != null) {
            if (!this.isReadOnly) {
                return arrs;
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

    public abstract ShortBuffer asReadOnlyBuffer();

    @Override
    public Buffer clear() {
        return super.clear();
    }

    public abstract ShortBuffer compact();

    @Override
    public int compareTo(ShortBuffer shortBuffer) {
        int n = this.position();
        int n2 = Math.min(this.remaining(), shortBuffer.remaining());
        int n3 = this.position();
        int n4 = shortBuffer.position();
        while (n3 < n + n2) {
            int n5 = ShortBuffer.compare(this.get(n3), shortBuffer.get(n4));
            if (n5 != 0) {
                return n5;
            }
            ++n3;
            ++n4;
        }
        return this.remaining() - shortBuffer.remaining();
    }

    public abstract ShortBuffer duplicate();

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ShortBuffer)) {
            return false;
        }
        object = (ShortBuffer)object;
        if (this.remaining() != ((Buffer)object).remaining()) {
            return false;
        }
        int n = this.position();
        int n2 = this.limit() - 1;
        int n3 = ((Buffer)object).limit() - 1;
        while (n2 >= n) {
            if (!ShortBuffer.equals(this.get(n2), ((ShortBuffer)object).get(n3))) {
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

    public ShortBuffer get(short[] arrs) {
        return this.get(arrs, 0, arrs.length);
    }

    public ShortBuffer get(short[] arrs, int n, int n2) {
        ShortBuffer.checkBounds(n, n2, arrs.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                arrs[i] = this.get();
            }
            return this;
        }
        throw new BufferUnderflowException();
    }

    public abstract short get();

    public abstract short get(int var1);

    @Override
    public final boolean hasArray() {
        boolean bl = this.hb != null && !this.isReadOnly;
        return bl;
    }

    public int hashCode() {
        int n = 1;
        int n2 = this.position();
        for (int i = this.limit() - 1; i >= n2; --i) {
            n = n * 31 + this.get(i);
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

    public abstract ShortBuffer put(int var1, short var2);

    public ShortBuffer put(ShortBuffer shortBuffer) {
        if (shortBuffer != this) {
            if (!this.isReadOnly()) {
                int n = shortBuffer.remaining();
                if (n <= this.remaining()) {
                    for (int i = 0; i < n; ++i) {
                        this.put(shortBuffer.get());
                    }
                    return this;
                }
                throw new BufferOverflowException();
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalArgumentException();
    }

    public abstract ShortBuffer put(short var1);

    public final ShortBuffer put(short[] arrs) {
        return this.put(arrs, 0, arrs.length);
    }

    public ShortBuffer put(short[] arrs, int n, int n2) {
        ShortBuffer.checkBounds(n, n2, arrs.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                this.put(arrs[i]);
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

    public abstract ShortBuffer slice();

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

