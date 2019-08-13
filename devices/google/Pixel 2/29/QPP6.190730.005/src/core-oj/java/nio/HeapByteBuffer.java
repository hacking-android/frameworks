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
import java.nio.ByteBuffer;
import java.nio.ByteBufferAsCharBuffer;
import java.nio.ByteBufferAsDoubleBuffer;
import java.nio.ByteBufferAsFloatBuffer;
import java.nio.ByteBufferAsIntBuffer;
import java.nio.ByteBufferAsLongBuffer;
import java.nio.ByteBufferAsShortBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.ShortBuffer;
import libcore.io.Memory;

final class HeapByteBuffer
extends ByteBuffer {
    HeapByteBuffer(int n, int n2) {
        this(n, n2, false);
    }

    private HeapByteBuffer(int n, int n2, boolean bl) {
        super(-1, 0, n2, n, new byte[n], 0);
        this.isReadOnly = bl;
    }

    HeapByteBuffer(byte[] arrby, int n, int n2) {
        this(arrby, n, n2, false);
    }

    private HeapByteBuffer(byte[] arrby, int n, int n2, int n3, int n4, int n5, boolean bl) {
        super(n, n2, n3, n4, arrby, n5);
        this.isReadOnly = bl;
    }

    private HeapByteBuffer(byte[] arrby, int n, int n2, boolean bl) {
        super(-1, n, n + n2, arrby.length, arrby, 0);
        this.isReadOnly = bl;
    }

    @Override
    byte _get(int n) {
        return this.hb[n];
    }

    @Override
    void _put(int n, byte by) {
        if (!this.isReadOnly) {
            this.hb[n] = by;
            return;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public CharBuffer asCharBuffer() {
        int n = this.remaining() >> 1;
        return new ByteBufferAsCharBuffer(this, -1, 0, n, n, this.position(), this.order());
    }

    @Override
    public DoubleBuffer asDoubleBuffer() {
        int n = this.remaining() >> 3;
        return new ByteBufferAsDoubleBuffer(this, -1, 0, n, n, this.position(), this.order());
    }

    @Override
    public FloatBuffer asFloatBuffer() {
        int n = this.remaining() >> 2;
        return new ByteBufferAsFloatBuffer(this, -1, 0, n, n, this.position(), this.order());
    }

    @Override
    public IntBuffer asIntBuffer() {
        int n = this.remaining() >> 2;
        return new ByteBufferAsIntBuffer(this, -1, 0, n, n, this.position(), this.order());
    }

    @Override
    public LongBuffer asLongBuffer() {
        int n = this.remaining() >> 3;
        return new ByteBufferAsLongBuffer(this, -1, 0, n, n, this.position(), this.order());
    }

    @Override
    public ByteBuffer asReadOnlyBuffer() {
        return new HeapByteBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, true);
    }

    @Override
    public ShortBuffer asShortBuffer() {
        int n = this.remaining() >> 1;
        return new ByteBufferAsShortBuffer(this, -1, 0, n, n, this.position(), this.order());
    }

    @Override
    public ByteBuffer compact() {
        if (!this.isReadOnly) {
            System.arraycopy(this.hb, this.ix(this.position()), this.hb, this.ix(0), this.remaining());
            this.position(this.remaining());
            this.limit(this.capacity());
            this.discardMark();
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuffer duplicate() {
        return new HeapByteBuffer(this.hb, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.isReadOnly);
    }

    @Override
    public byte get() {
        return this.hb[this.ix(this.nextGetIndex())];
    }

    @Override
    public byte get(int n) {
        return this.hb[this.ix(this.checkIndex(n))];
    }

    @Override
    public ByteBuffer get(byte[] arrby, int n, int n2) {
        HeapByteBuffer.checkBounds(n, n2, arrby.length);
        if (n2 <= this.remaining()) {
            System.arraycopy(this.hb, this.ix(this.position()), arrby, n, n2);
            this.position(this.position() + n2);
            return this;
        }
        throw new BufferUnderflowException();
    }

    @Override
    public char getChar() {
        return Bits.getChar(this, this.ix(this.nextGetIndex(2)), this.bigEndian);
    }

    @Override
    public char getChar(int n) {
        return Bits.getChar(this, this.ix(this.checkIndex(n, 2)), this.bigEndian);
    }

    @Override
    char getCharUnchecked(int n) {
        return Bits.getChar(this, this.ix(n), this.bigEndian);
    }

    @Override
    public double getDouble() {
        return Bits.getDouble(this, this.ix(this.nextGetIndex(8)), this.bigEndian);
    }

    @Override
    public double getDouble(int n) {
        return Bits.getDouble(this, this.ix(this.checkIndex(n, 8)), this.bigEndian);
    }

    @Override
    double getDoubleUnchecked(int n) {
        return Bits.getDouble(this, this.ix(n), this.bigEndian);
    }

    @Override
    public float getFloat() {
        return Bits.getFloat(this, this.ix(this.nextGetIndex(4)), this.bigEndian);
    }

    @Override
    public float getFloat(int n) {
        return Bits.getFloat(this, this.ix(this.checkIndex(n, 4)), this.bigEndian);
    }

    @Override
    float getFloatUnchecked(int n) {
        return Bits.getFloat(this, this.ix(n), this.bigEndian);
    }

    @Override
    public int getInt() {
        return Bits.getInt(this, this.ix(this.nextGetIndex(4)), this.bigEndian);
    }

    @Override
    public int getInt(int n) {
        return Bits.getInt(this, this.ix(this.checkIndex(n, 4)), this.bigEndian);
    }

    @Override
    int getIntUnchecked(int n) {
        return Bits.getInt(this, this.ix(n), this.bigEndian);
    }

    @Override
    public long getLong() {
        return Bits.getLong(this, this.ix(this.nextGetIndex(8)), this.bigEndian);
    }

    @Override
    public long getLong(int n) {
        return Bits.getLong(this, this.ix(this.checkIndex(n, 8)), this.bigEndian);
    }

    @Override
    long getLongUnchecked(int n) {
        return Bits.getLong(this, this.ix(n), this.bigEndian);
    }

    @Override
    public short getShort() {
        return Bits.getShort(this, this.ix(this.nextGetIndex(2)), this.bigEndian);
    }

    @Override
    public short getShort(int n) {
        return Bits.getShort(this, this.ix(this.checkIndex(n, 2)), this.bigEndian);
    }

    @Override
    short getShortUnchecked(int n) {
        return Bits.getShort(this, this.ix(n), this.bigEndian);
    }

    @Override
    void getUnchecked(int n, char[] arrc, int n2, int n3) {
        Memory.unsafeBulkGet((Object)arrc, (int)n2, (int)(n3 * 2), (byte[])this.hb, (int)this.ix(n), (int)2, (boolean)(this.nativeByteOrder ^ true));
    }

    @Override
    void getUnchecked(int n, double[] arrd, int n2, int n3) {
        Memory.unsafeBulkGet((Object)arrd, (int)n2, (int)(n3 * 8), (byte[])this.hb, (int)this.ix(n), (int)8, (boolean)(this.nativeByteOrder ^ true));
    }

    @Override
    void getUnchecked(int n, float[] arrf, int n2, int n3) {
        Memory.unsafeBulkGet((Object)arrf, (int)n2, (int)(n3 * 4), (byte[])this.hb, (int)this.ix(n), (int)4, (boolean)(this.nativeByteOrder ^ true));
    }

    @Override
    void getUnchecked(int n, int[] arrn, int n2, int n3) {
        Memory.unsafeBulkGet((Object)arrn, (int)n2, (int)(n3 * 4), (byte[])this.hb, (int)this.ix(n), (int)4, (boolean)(this.nativeByteOrder ^ true));
    }

    @Override
    void getUnchecked(int n, long[] arrl, int n2, int n3) {
        Memory.unsafeBulkGet((Object)arrl, (int)n2, (int)(n3 * 8), (byte[])this.hb, (int)this.ix(n), (int)8, (boolean)(this.nativeByteOrder ^ true));
    }

    @Override
    void getUnchecked(int n, short[] arrs, int n2, int n3) {
        Memory.unsafeBulkGet((Object)arrs, (int)n2, (int)(n3 * 2), (byte[])this.hb, (int)this.ix(n), (int)2, (boolean)(this.nativeByteOrder ^ true));
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
    public ByteBuffer put(byte by) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.nextPutIndex())] = by;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuffer put(int n, byte by) {
        if (!this.isReadOnly) {
            this.hb[this.ix((int)this.checkIndex((int)n))] = by;
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuffer put(byte[] arrby, int n, int n2) {
        if (!this.isReadOnly) {
            HeapByteBuffer.checkBounds(n, n2, arrby.length);
            if (n2 <= this.remaining()) {
                System.arraycopy(arrby, n, this.hb, this.ix(this.position()), n2);
                this.position(this.position() + n2);
                return this;
            }
            throw new BufferOverflowException();
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuffer putChar(char c) {
        if (!this.isReadOnly) {
            Bits.putChar(this, this.ix(this.nextPutIndex(2)), c, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuffer putChar(int n, char c) {
        if (!this.isReadOnly) {
            Bits.putChar(this, this.ix(this.checkIndex(n, 2)), c, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    void putCharUnchecked(int n, char c) {
        Bits.putChar(this, this.ix(n), c, this.bigEndian);
    }

    @Override
    public ByteBuffer putDouble(double d) {
        if (!this.isReadOnly) {
            Bits.putDouble(this, this.ix(this.nextPutIndex(8)), d, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuffer putDouble(int n, double d) {
        if (!this.isReadOnly) {
            Bits.putDouble(this, this.ix(this.checkIndex(n, 8)), d, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    void putDoubleUnchecked(int n, double d) {
        Bits.putDouble(this, this.ix(n), d, this.bigEndian);
    }

    @Override
    public ByteBuffer putFloat(float f) {
        if (!this.isReadOnly) {
            Bits.putFloat(this, this.ix(this.nextPutIndex(4)), f, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuffer putFloat(int n, float f) {
        if (!this.isReadOnly) {
            Bits.putFloat(this, this.ix(this.checkIndex(n, 4)), f, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    void putFloatUnchecked(int n, float f) {
        Bits.putFloat(this, this.ix(n), f, this.bigEndian);
    }

    @Override
    public ByteBuffer putInt(int n) {
        if (!this.isReadOnly) {
            Bits.putInt(this, this.ix(this.nextPutIndex(4)), n, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuffer putInt(int n, int n2) {
        if (!this.isReadOnly) {
            Bits.putInt(this, this.ix(this.checkIndex(n, 4)), n2, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    void putIntUnchecked(int n, int n2) {
        Bits.putInt(this, this.ix(n), n2, this.bigEndian);
    }

    @Override
    public ByteBuffer putLong(int n, long l) {
        if (!this.isReadOnly) {
            Bits.putLong(this, this.ix(this.checkIndex(n, 8)), l, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuffer putLong(long l) {
        if (!this.isReadOnly) {
            Bits.putLong(this, this.ix(this.nextPutIndex(8)), l, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    void putLongUnchecked(int n, long l) {
        Bits.putLong(this, this.ix(n), l, this.bigEndian);
    }

    @Override
    public ByteBuffer putShort(int n, short s) {
        if (!this.isReadOnly) {
            Bits.putShort(this, this.ix(this.checkIndex(n, 2)), s, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuffer putShort(short s) {
        if (!this.isReadOnly) {
            Bits.putShort(this, this.ix(this.nextPutIndex(2)), s, this.bigEndian);
            return this;
        }
        throw new ReadOnlyBufferException();
    }

    @Override
    void putShortUnchecked(int n, short s) {
        Bits.putShort(this, this.ix(n), s, this.bigEndian);
    }

    @Override
    void putUnchecked(int n, char[] arrc, int n2, int n3) {
        Memory.unsafeBulkPut((byte[])this.hb, (int)this.ix(n), (int)(n3 * 2), (Object)arrc, (int)n2, (int)2, (boolean)(this.nativeByteOrder ^ true));
    }

    @Override
    void putUnchecked(int n, double[] arrd, int n2, int n3) {
        Memory.unsafeBulkPut((byte[])this.hb, (int)this.ix(n), (int)(n3 * 8), (Object)arrd, (int)n2, (int)8, (boolean)(this.nativeByteOrder ^ true));
    }

    @Override
    void putUnchecked(int n, float[] arrf, int n2, int n3) {
        Memory.unsafeBulkPut((byte[])this.hb, (int)this.ix(n), (int)(n3 * 4), (Object)arrf, (int)n2, (int)4, (boolean)(this.nativeByteOrder ^ true));
    }

    @Override
    void putUnchecked(int n, int[] arrn, int n2, int n3) {
        Memory.unsafeBulkPut((byte[])this.hb, (int)this.ix(n), (int)(n3 * 4), (Object)arrn, (int)n2, (int)4, (boolean)(this.nativeByteOrder ^ true));
    }

    @Override
    void putUnchecked(int n, long[] arrl, int n2, int n3) {
        Memory.unsafeBulkPut((byte[])this.hb, (int)this.ix(n), (int)(n3 * 8), (Object)arrl, (int)n2, (int)8, (boolean)(this.nativeByteOrder ^ true));
    }

    @Override
    void putUnchecked(int n, short[] arrs, int n2, int n3) {
        Memory.unsafeBulkPut((byte[])this.hb, (int)this.ix(n), (int)(n3 * 2), (Object)arrs, (int)n2, (int)2, (boolean)(this.nativeByteOrder ^ true));
    }

    @Override
    public ByteBuffer slice() {
        return new HeapByteBuffer(this.hb, -1, 0, this.remaining(), this.remaining(), this.position() + this.offset, this.isReadOnly);
    }
}

