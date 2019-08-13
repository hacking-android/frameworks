/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.HeapFloatBuffer;
import java.nio.ReadOnlyBufferException;

public abstract class FloatBuffer
extends Buffer
implements Comparable<FloatBuffer> {
    final float[] hb;
    boolean isReadOnly;
    final int offset;

    FloatBuffer(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, null, 0);
    }

    FloatBuffer(int n, int n2, int n3, int n4, float[] arrf, int n5) {
        super(n, n2, n3, n4, 2);
        this.hb = arrf;
        this.offset = n5;
    }

    public static FloatBuffer allocate(int n) {
        if (n >= 0) {
            return new HeapFloatBuffer(n, n);
        }
        throw new IllegalArgumentException();
    }

    private static int compare(float f, float f2) {
        int n = 0;
        if (f < f2) {
            n = -1;
        } else if (f > f2) {
            n = 1;
        } else if (f != f2) {
            if (Float.isNaN(f)) {
                if (!Float.isNaN(f2)) {
                    n = 1;
                }
            } else {
                n = -1;
            }
        }
        return n;
    }

    private static boolean equals(float f, float f2) {
        boolean bl = f == f2 || Float.isNaN(f) && Float.isNaN(f2);
        return bl;
    }

    public static FloatBuffer wrap(float[] arrf) {
        return FloatBuffer.wrap(arrf, 0, arrf.length);
    }

    public static FloatBuffer wrap(float[] object, int n, int n2) {
        try {
            object = new HeapFloatBuffer((float[])object, n, n2);
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException();
        }
    }

    public final float[] array() {
        float[] arrf = this.hb;
        if (arrf != null) {
            if (!this.isReadOnly) {
                return arrf;
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

    public abstract FloatBuffer asReadOnlyBuffer();

    @Override
    public Buffer clear() {
        return super.clear();
    }

    public abstract FloatBuffer compact();

    @Override
    public int compareTo(FloatBuffer floatBuffer) {
        int n = this.position();
        int n2 = Math.min(this.remaining(), floatBuffer.remaining());
        int n3 = this.position();
        int n4 = floatBuffer.position();
        while (n3 < n + n2) {
            int n5 = FloatBuffer.compare(this.get(n3), floatBuffer.get(n4));
            if (n5 != 0) {
                return n5;
            }
            ++n3;
            ++n4;
        }
        return this.remaining() - floatBuffer.remaining();
    }

    public abstract FloatBuffer duplicate();

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof FloatBuffer)) {
            return false;
        }
        object = (FloatBuffer)object;
        if (this.remaining() != ((Buffer)object).remaining()) {
            return false;
        }
        int n = this.position();
        int n2 = this.limit() - 1;
        int n3 = ((Buffer)object).limit() - 1;
        while (n2 >= n) {
            if (!FloatBuffer.equals(this.get(n2), ((FloatBuffer)object).get(n3))) {
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

    public abstract float get();

    public abstract float get(int var1);

    public FloatBuffer get(float[] arrf) {
        return this.get(arrf, 0, arrf.length);
    }

    public FloatBuffer get(float[] arrf, int n, int n2) {
        FloatBuffer.checkBounds(n, n2, arrf.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                arrf[i] = this.get();
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

    public abstract FloatBuffer put(float var1);

    public abstract FloatBuffer put(int var1, float var2);

    public FloatBuffer put(FloatBuffer floatBuffer) {
        if (floatBuffer != this) {
            if (!this.isReadOnly()) {
                int n = floatBuffer.remaining();
                if (n <= this.remaining()) {
                    for (int i = 0; i < n; ++i) {
                        this.put(floatBuffer.get());
                    }
                    return this;
                }
                throw new BufferOverflowException();
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalArgumentException();
    }

    public final FloatBuffer put(float[] arrf) {
        return this.put(arrf, 0, arrf.length);
    }

    public FloatBuffer put(float[] arrf, int n, int n2) {
        FloatBuffer.checkBounds(n, n2, arrf.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                this.put(arrf[i]);
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

    public abstract FloatBuffer slice();

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

