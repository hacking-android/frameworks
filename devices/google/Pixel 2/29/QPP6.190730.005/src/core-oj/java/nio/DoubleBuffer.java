/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.HeapDoubleBuffer;
import java.nio.ReadOnlyBufferException;

public abstract class DoubleBuffer
extends Buffer
implements Comparable<DoubleBuffer> {
    final double[] hb;
    boolean isReadOnly;
    final int offset;

    DoubleBuffer(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, null, 0);
    }

    DoubleBuffer(int n, int n2, int n3, int n4, double[] arrd, int n5) {
        super(n, n2, n3, n4, 3);
        this.hb = arrd;
        this.offset = n5;
    }

    public static DoubleBuffer allocate(int n) {
        if (n >= 0) {
            return new HeapDoubleBuffer(n, n);
        }
        throw new IllegalArgumentException();
    }

    private static int compare(double d, double d2) {
        int n = 0;
        if (d < d2) {
            n = -1;
        } else if (d > d2) {
            n = 1;
        } else if (d != d2) {
            if (Double.isNaN(d)) {
                if (!Double.isNaN(d2)) {
                    n = 1;
                }
            } else {
                n = -1;
            }
        }
        return n;
    }

    private static boolean equals(double d, double d2) {
        boolean bl = d == d2 || Double.isNaN(d) && Double.isNaN(d2);
        return bl;
    }

    public static DoubleBuffer wrap(double[] arrd) {
        return DoubleBuffer.wrap(arrd, 0, arrd.length);
    }

    public static DoubleBuffer wrap(double[] object, int n, int n2) {
        try {
            object = new HeapDoubleBuffer((double[])object, n, n2);
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException();
        }
    }

    public final double[] array() {
        double[] arrd = this.hb;
        if (arrd != null) {
            if (!this.isReadOnly) {
                return arrd;
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

    public abstract DoubleBuffer asReadOnlyBuffer();

    @Override
    public Buffer clear() {
        return super.clear();
    }

    public abstract DoubleBuffer compact();

    @Override
    public int compareTo(DoubleBuffer doubleBuffer) {
        int n = this.position();
        int n2 = Math.min(this.remaining(), doubleBuffer.remaining());
        int n3 = this.position();
        int n4 = doubleBuffer.position();
        while (n3 < n + n2) {
            int n5 = DoubleBuffer.compare(this.get(n3), doubleBuffer.get(n4));
            if (n5 != 0) {
                return n5;
            }
            ++n3;
            ++n4;
        }
        return this.remaining() - doubleBuffer.remaining();
    }

    public abstract DoubleBuffer duplicate();

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DoubleBuffer)) {
            return false;
        }
        object = (DoubleBuffer)object;
        if (this.remaining() != ((Buffer)object).remaining()) {
            return false;
        }
        int n = this.position();
        int n2 = this.limit() - 1;
        int n3 = ((Buffer)object).limit() - 1;
        while (n2 >= n) {
            if (!DoubleBuffer.equals(this.get(n2), ((DoubleBuffer)object).get(n3))) {
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

    public abstract double get();

    public abstract double get(int var1);

    public DoubleBuffer get(double[] arrd) {
        return this.get(arrd, 0, arrd.length);
    }

    public DoubleBuffer get(double[] arrd, int n, int n2) {
        DoubleBuffer.checkBounds(n, n2, arrd.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                arrd[i] = this.get();
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

    public abstract DoubleBuffer put(double var1);

    public abstract DoubleBuffer put(int var1, double var2);

    public DoubleBuffer put(DoubleBuffer doubleBuffer) {
        if (doubleBuffer != this) {
            if (!this.isReadOnly()) {
                int n = doubleBuffer.remaining();
                if (n <= this.remaining()) {
                    for (int i = 0; i < n; ++i) {
                        this.put(doubleBuffer.get());
                    }
                    return this;
                }
                throw new BufferOverflowException();
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalArgumentException();
    }

    public final DoubleBuffer put(double[] arrd) {
        return this.put(arrd, 0, arrd.length);
    }

    public DoubleBuffer put(double[] arrd, int n, int n2) {
        DoubleBuffer.checkBounds(n, n2, arrd.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                this.put(arrd[i]);
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

    public abstract DoubleBuffer slice();

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

