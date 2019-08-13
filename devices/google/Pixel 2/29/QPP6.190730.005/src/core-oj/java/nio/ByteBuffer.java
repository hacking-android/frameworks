/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.Memory
 */
package java.nio;

import java.nio.Bits;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DirectByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.HeapByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.ShortBuffer;
import libcore.io.Memory;

public abstract class ByteBuffer
extends Buffer
implements Comparable<ByteBuffer> {
    boolean bigEndian;
    final byte[] hb;
    boolean isReadOnly;
    boolean nativeByteOrder;
    final int offset;

    ByteBuffer(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, null, 0);
    }

    ByteBuffer(int n, int n2, int n3, int n4, byte[] arrby, int n5) {
        super(n, n2, n3, n4, 0);
        boolean bl = true;
        this.bigEndian = true;
        if (Bits.byteOrder() != ByteOrder.BIG_ENDIAN) {
            bl = false;
        }
        this.nativeByteOrder = bl;
        this.hb = arrby;
        this.offset = n5;
    }

    public static ByteBuffer allocate(int n) {
        if (n >= 0) {
            return new HeapByteBuffer(n, n);
        }
        throw new IllegalArgumentException();
    }

    public static ByteBuffer allocateDirect(int n) {
        return new DirectByteBuffer(n, new DirectByteBuffer.MemoryRef(n));
    }

    private static int compare(byte by, byte by2) {
        return Byte.compare(by, by2);
    }

    private static boolean equals(byte by, byte by2) {
        boolean bl = by == by2;
        return bl;
    }

    public static ByteBuffer wrap(byte[] arrby) {
        return ByteBuffer.wrap(arrby, 0, arrby.length);
    }

    public static ByteBuffer wrap(byte[] object, int n, int n2) {
        try {
            object = new HeapByteBuffer((byte[])object, n, n2);
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException();
        }
    }

    abstract byte _get(int var1);

    abstract void _put(int var1, byte var2);

    public final byte[] array() {
        byte[] arrby = this.hb;
        if (arrby != null) {
            if (!this.isReadOnly) {
                return arrby;
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

    public abstract CharBuffer asCharBuffer();

    public abstract DoubleBuffer asDoubleBuffer();

    public abstract FloatBuffer asFloatBuffer();

    public abstract IntBuffer asIntBuffer();

    public abstract LongBuffer asLongBuffer();

    public abstract ByteBuffer asReadOnlyBuffer();

    public abstract ShortBuffer asShortBuffer();

    @Override
    public Buffer clear() {
        return super.clear();
    }

    public abstract ByteBuffer compact();

    @Override
    public int compareTo(ByteBuffer byteBuffer) {
        int n = this.position();
        int n2 = Math.min(this.remaining(), byteBuffer.remaining());
        int n3 = this.position();
        int n4 = byteBuffer.position();
        while (n3 < n + n2) {
            int n5 = ByteBuffer.compare(this.get(n3), byteBuffer.get(n4));
            if (n5 != 0) {
                return n5;
            }
            ++n3;
            ++n4;
        }
        return this.remaining() - byteBuffer.remaining();
    }

    public abstract ByteBuffer duplicate();

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ByteBuffer)) {
            return false;
        }
        object = (ByteBuffer)object;
        if (this.remaining() != ((Buffer)object).remaining()) {
            return false;
        }
        int n = this.position();
        int n2 = this.limit() - 1;
        int n3 = ((Buffer)object).limit() - 1;
        while (n2 >= n) {
            if (!ByteBuffer.equals(this.get(n2), ((ByteBuffer)object).get(n3))) {
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

    public abstract byte get();

    public abstract byte get(int var1);

    public ByteBuffer get(byte[] arrby) {
        return this.get(arrby, 0, arrby.length);
    }

    public ByteBuffer get(byte[] arrby, int n, int n2) {
        ByteBuffer.checkBounds(n, n2, arrby.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                arrby[i] = this.get();
            }
            return this;
        }
        throw new BufferUnderflowException();
    }

    public abstract char getChar();

    public abstract char getChar(int var1);

    abstract char getCharUnchecked(int var1);

    public abstract double getDouble();

    public abstract double getDouble(int var1);

    abstract double getDoubleUnchecked(int var1);

    public abstract float getFloat();

    public abstract float getFloat(int var1);

    abstract float getFloatUnchecked(int var1);

    public abstract int getInt();

    public abstract int getInt(int var1);

    abstract int getIntUnchecked(int var1);

    public abstract long getLong();

    public abstract long getLong(int var1);

    abstract long getLongUnchecked(int var1);

    public abstract short getShort();

    public abstract short getShort(int var1);

    abstract short getShortUnchecked(int var1);

    abstract void getUnchecked(int var1, char[] var2, int var3, int var4);

    abstract void getUnchecked(int var1, double[] var2, int var3, int var4);

    abstract void getUnchecked(int var1, float[] var2, int var3, int var4);

    abstract void getUnchecked(int var1, int[] var2, int var3, int var4);

    abstract void getUnchecked(int var1, long[] var2, int var3, int var4);

    abstract void getUnchecked(int var1, short[] var2, int var3, int var4);

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

    public boolean isAccessible() {
        return true;
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

    public final ByteBuffer order(ByteOrder byteOrder) {
        ByteOrder byteOrder2 = ByteOrder.BIG_ENDIAN;
        boolean bl = true;
        boolean bl2 = byteOrder == byteOrder2;
        boolean bl3 = this.bigEndian = bl2;
        bl2 = Bits.byteOrder() == ByteOrder.BIG_ENDIAN;
        bl2 = bl3 == bl2 ? bl : false;
        this.nativeByteOrder = bl2;
        return this;
    }

    public final ByteOrder order() {
        ByteOrder byteOrder = this.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        return byteOrder;
    }

    @Override
    public Buffer position(int n) {
        return super.position(n);
    }

    public abstract ByteBuffer put(byte var1);

    public abstract ByteBuffer put(int var1, byte var2);

    public ByteBuffer put(ByteBuffer byteBuffer) {
        if (byteBuffer != this) {
            if (!this.isReadOnly()) {
                int n = byteBuffer.remaining();
                if (n <= this.remaining()) {
                    Object object;
                    if (this.hb != null && (object = byteBuffer.hb) != null) {
                        System.arraycopy(object, byteBuffer.position() + byteBuffer.offset, this.hb, this.position() + this.offset, n);
                    } else {
                        int n2;
                        object = byteBuffer.isDirect() ? byteBuffer : byteBuffer.hb;
                        int n3 = n2 = byteBuffer.position();
                        if (!byteBuffer.isDirect()) {
                            n3 = n2 + byteBuffer.offset;
                        }
                        byte[] arrby = this.isDirect() ? this : this.hb;
                        n2 = this.position();
                        if (!this.isDirect()) {
                            n2 += this.offset;
                        }
                        Memory.memmove((Object)arrby, (int)n2, (Object)object, (int)n3, (long)n);
                    }
                    byteBuffer.position(byteBuffer.limit());
                    this.position(this.position() + n);
                    return this;
                }
                throw new BufferOverflowException();
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalArgumentException();
    }

    public final ByteBuffer put(byte[] arrby) {
        return this.put(arrby, 0, arrby.length);
    }

    public ByteBuffer put(byte[] arrby, int n, int n2) {
        ByteBuffer.checkBounds(n, n2, arrby.length);
        if (n2 <= this.remaining()) {
            for (int i = n; i < n + n2; ++i) {
                this.put(arrby[i]);
            }
            return this;
        }
        throw new BufferOverflowException();
    }

    public abstract ByteBuffer putChar(char var1);

    public abstract ByteBuffer putChar(int var1, char var2);

    abstract void putCharUnchecked(int var1, char var2);

    public abstract ByteBuffer putDouble(double var1);

    public abstract ByteBuffer putDouble(int var1, double var2);

    abstract void putDoubleUnchecked(int var1, double var2);

    public abstract ByteBuffer putFloat(float var1);

    public abstract ByteBuffer putFloat(int var1, float var2);

    abstract void putFloatUnchecked(int var1, float var2);

    public abstract ByteBuffer putInt(int var1);

    public abstract ByteBuffer putInt(int var1, int var2);

    abstract void putIntUnchecked(int var1, int var2);

    public abstract ByteBuffer putLong(int var1, long var2);

    public abstract ByteBuffer putLong(long var1);

    abstract void putLongUnchecked(int var1, long var2);

    public abstract ByteBuffer putShort(int var1, short var2);

    public abstract ByteBuffer putShort(short var1);

    abstract void putShortUnchecked(int var1, short var2);

    abstract void putUnchecked(int var1, char[] var2, int var3, int var4);

    abstract void putUnchecked(int var1, double[] var2, int var3, int var4);

    abstract void putUnchecked(int var1, float[] var2, int var3, int var4);

    abstract void putUnchecked(int var1, int[] var2, int var3, int var4);

    abstract void putUnchecked(int var1, long[] var2, int var3, int var4);

    abstract void putUnchecked(int var1, short[] var2, int var3, int var4);

    @Override
    public Buffer reset() {
        return super.reset();
    }

    @Override
    public Buffer rewind() {
        return super.rewind();
    }

    public void setAccessible(boolean bl) {
        throw new UnsupportedOperationException();
    }

    public abstract ByteBuffer slice();

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

