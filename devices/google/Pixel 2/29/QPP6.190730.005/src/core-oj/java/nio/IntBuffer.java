/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.HeapIntBuffer;
import java.nio.ReadOnlyBufferException;

public abstract class IntBuffer
extends Buffer
implements Comparable<IntBuffer> {
    final int[] hb;
    boolean isReadOnly;
    final int offset;

    IntBuffer(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, null, 0);
    }

    IntBuffer(int n, int n2, int n3, int n4, int[] arrn, int n5) {
        super(n, n2, n3, n4, 2);
        this.hb = arrn;
        this.offset = n5;
    }

    public static IntBuffer allocate(int n) {
        if (n >= 0) {
            return new HeapIntBuffer(n, n);
        }
        throw new IllegalArgumentException();
    }

    private static int compare(int n, int n2) {
        return Integer.compare(n, n2);
    }

    private static boolean equals(int n, int n2) {
        boolean bl = n == n2;
        return bl;
    }

    public static IntBuffer wrap(int[] arrn) {
        return IntBuffer.wrap(arrn, 0, arrn.length);
    }

    public static IntBuffer wrap(int[] object, int n, int n2) {
        try {
            object = new HeapIntBuffer((int[])object, n, n2);
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException();
        }
    }

    public final int[] array() {
        int[] arrn = this.hb;
        if (arrn != null) {
            if (!this.isReadOnly) {
                return arrn;
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

    public abstract IntBuffer asReadOnlyBuffer();

    @Override
    public Buffer clear() {
        return super.clear();
    }

    public abstract IntBuffer compact();

    @Override
    public int compareTo(IntBuffer intBuffer) {
        int n = this.position();
        int n2 = Math.min(this.remaining(), intBuffer.remaining());
        int n3 = this.position();
        int n4 = intBuffer.position();
        while (n3 < n + n2) {
            int n5 = IntBuffer.compare(this.get(n3), intBuffer.get(n4));
            if (n5 != 0) {
                return n5;
            }
            ++n3;
            ++n4;
        }
        return this.remaining() - intBuffer.remaining();
    }

    public abstract IntBuffer duplicate();

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof IntBuffer)) {
            return false;
        }
        object = (IntBuffer)object;
        if (this.remaining() != ((Buffer)object).remaining()) {
            return false;
        }
        int n = this.position();
        int n2 = this.limit() - 1;
        int n3 = ((Buffer)object).limit() - 1;
        while (n2 >= n) {
            if (!IntBuffer.equals(this.get(n2), ((IntBuffer)object).get(n3))) {
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

    public abstract int get();

    public abstract int get(int var1);

    public IntBuffer get(int[] arrn) {
        return this.get(arrn, 0, arrn.length);
    }

    public IntBuffer get(int[] arrn, int n, int n2) {
        IntBuffer.checkBounds(n, n2, arrn.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                arrn[i] = this.get();
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

    public abstract IntBuffer put(int var1);

    public abstract IntBuffer put(int var1, int var2);

    public IntBuffer put(IntBuffer intBuffer) {
        if (intBuffer != this) {
            if (!this.isReadOnly()) {
                int n = intBuffer.remaining();
                if (n <= this.remaining()) {
                    for (int i = 0; i < n; ++i) {
                        this.put(intBuffer.get());
                    }
                    return this;
                }
                throw new BufferOverflowException();
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalArgumentException();
    }

    public final IntBuffer put(int[] arrn) {
        return this.put(arrn, 0, arrn.length);
    }

    public IntBuffer put(int[] arrn, int n, int n2) {
        IntBuffer.checkBounds(n, n2, arrn.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                this.put(arrn[i]);
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

    public abstract IntBuffer slice();

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

