/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 *  libcore.io.Memory
 */
package java.nio;

import dalvik.system.VMRuntime;
import java.io.FileDescriptor;
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
import java.nio.MappedByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.ShortBuffer;
import libcore.io.Memory;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

public class DirectByteBuffer
extends MappedByteBuffer
implements DirectBuffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    final Cleaner cleaner;
    final MemoryRef memoryRef;

    public DirectByteBuffer(int n, long l, FileDescriptor fileDescriptor, Runnable runnable, boolean bl) {
        super(-1, 0, n, n, fileDescriptor);
        this.isReadOnly = bl;
        this.memoryRef = new MemoryRef(l, null);
        this.address = l;
        this.cleaner = Cleaner.create(this.memoryRef, runnable);
    }

    DirectByteBuffer(int n, MemoryRef memoryRef) {
        super(-1, 0, n, n, memoryRef.buffer, memoryRef.offset);
        this.memoryRef = memoryRef;
        this.address = memoryRef.allocatedAddress + (long)memoryRef.offset;
        this.cleaner = null;
        this.isReadOnly = false;
    }

    private DirectByteBuffer(long l, int n) {
        super(-1, 0, n, n);
        this.memoryRef = new MemoryRef(l, this);
        this.address = l;
        this.cleaner = null;
    }

    DirectByteBuffer(MemoryRef memoryRef, int n, int n2, int n3, int n4, int n5) {
        this(memoryRef, n, n2, n3, n4, n5, false);
    }

    DirectByteBuffer(MemoryRef memoryRef, int n, int n2, int n3, int n4, int n5, boolean bl) {
        super(n, n2, n3, n4, memoryRef.buffer, n5);
        this.isReadOnly = bl;
        this.memoryRef = memoryRef;
        this.address = memoryRef.allocatedAddress + (long)n5;
        this.cleaner = null;
    }

    private byte get(long l) {
        return Memory.peekByte((long)l);
    }

    private double getDouble(long l) {
        return Double.longBitsToDouble(Memory.peekLong((long)l, (boolean)(this.nativeByteOrder ^ true)));
    }

    private float getFloat(long l) {
        return Float.intBitsToFloat(Memory.peekInt((long)l, (boolean)(this.nativeByteOrder ^ true)));
    }

    private int getInt(long l) {
        return Memory.peekInt((long)l, (boolean)(this.nativeByteOrder ^ true));
    }

    private long getLong(long l) {
        return Memory.peekLong((long)l, (boolean)(this.nativeByteOrder ^ true));
    }

    private short getShort(long l) {
        return Memory.peekShort((long)l, (boolean)(this.nativeByteOrder ^ true));
    }

    private long ix(int n) {
        return this.address + (long)n;
    }

    private ByteBuffer put(long l, byte by) {
        Memory.pokeByte((long)l, (byte)by);
        return this;
    }

    private ByteBuffer putChar(long l, char c) {
        Memory.pokeShort((long)l, (short)((short)c), (boolean)(this.nativeByteOrder ^ true));
        return this;
    }

    private ByteBuffer putDouble(long l, double d) {
        Memory.pokeLong((long)l, (long)Double.doubleToRawLongBits(d), (boolean)(this.nativeByteOrder ^ true));
        return this;
    }

    private ByteBuffer putFloat(long l, float f) {
        Memory.pokeInt((long)l, (int)Float.floatToRawIntBits(f), (boolean)(this.nativeByteOrder ^ true));
        return this;
    }

    private ByteBuffer putInt(long l, int n) {
        Memory.pokeInt((long)l, (int)n, (boolean)(this.nativeByteOrder ^ true));
        return this;
    }

    private ByteBuffer putLong(long l, long l2) {
        Memory.pokeLong((long)l, (long)l2, (boolean)(this.nativeByteOrder ^ true));
        return this;
    }

    private ByteBuffer putShort(long l, short s) {
        Memory.pokeShort((long)l, (short)s, (boolean)(this.nativeByteOrder ^ true));
        return this;
    }

    @Override
    final byte _get(int n) {
        return this.get(n);
    }

    @Override
    final void _put(int n, byte by) {
        this.put(n, by);
    }

    @Override
    public final long address() {
        return this.address;
    }

    @Override
    public final CharBuffer asCharBuffer() {
        if (!this.memoryRef.isFreed) {
            int n;
            int n2 = this.position();
            n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
            return new ByteBufferAsCharBuffer(this, -1, 0, n >>= 1, n, n2, this.order());
        }
        throw new IllegalStateException("buffer has been freed");
    }

    @Override
    public final DoubleBuffer asDoubleBuffer() {
        if (!this.memoryRef.isFreed) {
            int n;
            int n2 = this.position();
            n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
            return new ByteBufferAsDoubleBuffer(this, -1, 0, n >>= 3, n, n2, this.order());
        }
        throw new IllegalStateException("buffer has been freed");
    }

    @Override
    public final FloatBuffer asFloatBuffer() {
        if (!this.memoryRef.isFreed) {
            int n;
            int n2 = this.position();
            n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
            return new ByteBufferAsFloatBuffer(this, -1, 0, n >>= 2, n, n2, this.order());
        }
        throw new IllegalStateException("buffer has been freed");
    }

    @Override
    public final IntBuffer asIntBuffer() {
        if (!this.memoryRef.isFreed) {
            int n;
            int n2 = this.position();
            n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
            return new ByteBufferAsIntBuffer(this, -1, 0, n >>= 2, n, n2, this.order());
        }
        throw new IllegalStateException("buffer has been freed");
    }

    @Override
    public final LongBuffer asLongBuffer() {
        if (!this.memoryRef.isFreed) {
            int n;
            int n2 = this.position();
            n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
            return new ByteBufferAsLongBuffer(this, -1, 0, n >>= 3, n, n2, this.order());
        }
        throw new IllegalStateException("buffer has been freed");
    }

    @Override
    public final ByteBuffer asReadOnlyBuffer() {
        if (!this.memoryRef.isFreed) {
            return new DirectByteBuffer(this.memoryRef, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, true);
        }
        throw new IllegalStateException("buffer has been freed");
    }

    @Override
    public final ShortBuffer asShortBuffer() {
        if (!this.memoryRef.isFreed) {
            int n;
            int n2 = this.position();
            n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
            return new ByteBufferAsShortBuffer(this, -1, 0, n >>= 1, n, n2, this.order());
        }
        throw new IllegalStateException("buffer has been freed");
    }

    @Override
    public final Object attachment() {
        return this.memoryRef;
    }

    @Override
    public final Cleaner cleaner() {
        return this.cleaner;
    }

    @Override
    public final ByteBuffer compact() {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                int n;
                int n2 = this.position();
                n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
                System.arraycopy(this.hb, this.position + this.offset, this.hb, this.offset, this.remaining());
                this.position(n);
                this.limit(this.capacity());
                this.discardMark();
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer duplicate() {
        if (!this.memoryRef.isFreed) {
            return new DirectByteBuffer(this.memoryRef, this.markValue(), this.position(), this.limit(), this.capacity(), this.offset, this.isReadOnly);
        }
        throw new IllegalStateException("buffer has been freed");
    }

    @Override
    public final byte get() {
        if (this.memoryRef.isAccessible) {
            return this.get(this.ix(this.nextGetIndex()));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final byte get(int n) {
        if (this.memoryRef.isAccessible) {
            return this.get(this.ix(this.checkIndex(n)));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public ByteBuffer get(byte[] arrby, int n, int n2) {
        if (this.memoryRef.isAccessible) {
            DirectByteBuffer.checkBounds(n, n2, arrby.length);
            int n3 = this.position();
            int n4 = this.limit();
            n4 = n3 <= n4 ? (n4 -= n3) : 0;
            if (n2 <= n4) {
                Memory.peekByteArray((long)this.ix(n3), (byte[])arrby, (int)n, (int)n2);
                this.position = n3 + n2;
                return this;
            }
            throw new BufferUnderflowException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final char getChar() {
        if (this.memoryRef.isAccessible) {
            int n = this.position + 2;
            if (n <= this.limit()) {
                char c = (char)Memory.peekShort((long)this.ix(this.position), (boolean)(this.nativeByteOrder ^ true));
                this.position = n;
                return c;
            }
            throw new BufferUnderflowException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final char getChar(int n) {
        if (this.memoryRef.isAccessible) {
            this.checkIndex(n, 2);
            return (char)Memory.peekShort((long)this.ix(n), (boolean)(this.nativeByteOrder ^ true));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    char getCharUnchecked(int n) {
        if (this.memoryRef.isAccessible) {
            return (char)Memory.peekShort((long)this.ix(n), (boolean)(this.nativeByteOrder ^ true));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final double getDouble() {
        if (this.memoryRef.isAccessible) {
            return this.getDouble(this.ix(this.nextGetIndex(8)));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final double getDouble(int n) {
        if (this.memoryRef.isAccessible) {
            return this.getDouble(this.ix(this.checkIndex(n, 8)));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final double getDoubleUnchecked(int n) {
        if (this.memoryRef.isAccessible) {
            return this.getDouble(this.ix(n));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final float getFloat() {
        if (this.memoryRef.isAccessible) {
            return this.getFloat(this.ix(this.nextGetIndex(4)));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final float getFloat(int n) {
        if (this.memoryRef.isAccessible) {
            return this.getFloat(this.ix(this.checkIndex(n, 4)));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final float getFloatUnchecked(int n) {
        if (this.memoryRef.isAccessible) {
            return this.getFloat(this.ix(n));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public int getInt() {
        if (this.memoryRef.isAccessible) {
            return this.getInt(this.ix(this.nextGetIndex(4)));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public int getInt(int n) {
        if (this.memoryRef.isAccessible) {
            return this.getInt(this.ix(this.checkIndex(n, 4)));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final int getIntUnchecked(int n) {
        if (this.memoryRef.isAccessible) {
            return this.getInt(this.ix(n));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final long getLong() {
        if (this.memoryRef.isAccessible) {
            return this.getLong(this.ix(this.nextGetIndex(8)));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final long getLong(int n) {
        if (this.memoryRef.isAccessible) {
            return this.getLong(this.ix(this.checkIndex(n, 8)));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final long getLongUnchecked(int n) {
        if (this.memoryRef.isAccessible) {
            return this.getLong(this.ix(n));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final short getShort() {
        if (this.memoryRef.isAccessible) {
            return this.getShort(this.ix(this.nextGetIndex(2)));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final short getShort(int n) {
        if (this.memoryRef.isAccessible) {
            return this.getShort(this.ix(this.checkIndex(n, 2)));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    short getShortUnchecked(int n) {
        if (this.memoryRef.isAccessible) {
            return this.getShort(this.ix(n));
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    void getUnchecked(int n, char[] arrc, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.peekCharArray((long)this.ix(n), (char[])arrc, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void getUnchecked(int n, double[] arrd, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.peekDoubleArray((long)this.ix(n), (double[])arrd, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void getUnchecked(int n, float[] arrf, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.peekFloatArray((long)this.ix(n), (float[])arrf, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void getUnchecked(int n, int[] arrn, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.peekIntArray((long)this.ix(n), (int[])arrn, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void getUnchecked(int n, long[] arrl, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.peekLongArray((long)this.ix(n), (long[])arrl, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    void getUnchecked(int n, short[] arrs, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.peekShortArray((long)this.ix(n), (short[])arrs, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final boolean isAccessible() {
        return this.memoryRef.isAccessible;
    }

    @Override
    public final boolean isDirect() {
        return true;
    }

    @Override
    public final boolean isReadOnly() {
        return this.isReadOnly;
    }

    @Override
    public final ByteBuffer put(byte by) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.put(this.ix(this.nextPutIndex()), by);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer put(int n, byte by) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.put(this.ix(this.checkIndex(n)), by);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public ByteBuffer put(ByteBuffer byteBuffer) {
        if (this.memoryRef.isAccessible) {
            return super.put(byteBuffer);
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public ByteBuffer put(byte[] arrby, int n, int n2) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                DirectByteBuffer.checkBounds(n, n2, arrby.length);
                int n3 = this.position();
                int n4 = this.limit();
                n4 = n3 <= n4 ? (n4 -= n3) : 0;
                if (n2 <= n4) {
                    Memory.pokeByteArray((long)this.ix(n3), (byte[])arrby, (int)n, (int)n2);
                    this.position = n3 + n2;
                    return this;
                }
                throw new BufferOverflowException();
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putChar(char c) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putChar(this.ix(this.nextPutIndex(2)), c);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putChar(int n, char c) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putChar(this.ix(this.checkIndex(n, 2)), c);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    void putCharUnchecked(int n, char c) {
        if (this.memoryRef.isAccessible) {
            this.putChar(this.ix(n), c);
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putDouble(double d) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putDouble(this.ix(this.nextPutIndex(8)), d);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putDouble(int n, double d) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putDouble(this.ix(this.checkIndex(n, 8)), d);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void putDoubleUnchecked(int n, double d) {
        if (this.memoryRef.isAccessible) {
            this.putDouble(this.ix(n), d);
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putFloat(float f) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putFloat(this.ix(this.nextPutIndex(4)), f);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putFloat(int n, float f) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putFloat(this.ix(this.checkIndex(n, 4)), f);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void putFloatUnchecked(int n, float f) {
        if (this.memoryRef.isAccessible) {
            this.putFloat(this.ix(n), f);
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putInt(int n) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putInt(this.ix(this.nextPutIndex(4)), n);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putInt(int n, int n2) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putInt(this.ix(this.checkIndex(n, 4)), n2);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void putIntUnchecked(int n, int n2) {
        if (this.memoryRef.isAccessible) {
            this.putInt(this.ix(n), n2);
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putLong(int n, long l) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putLong(this.ix(this.checkIndex(n, 8)), l);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putLong(long l) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putLong(this.ix(this.nextPutIndex(8)), l);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void putLongUnchecked(int n, long l) {
        if (this.memoryRef.isAccessible) {
            this.putLong(this.ix(n), l);
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putShort(int n, short s) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putShort(this.ix(this.checkIndex(n, 2)), s);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final ByteBuffer putShort(short s) {
        if (this.memoryRef.isAccessible) {
            if (!this.isReadOnly) {
                this.putShort(this.ix(this.nextPutIndex(2)), s);
                return this;
            }
            throw new ReadOnlyBufferException();
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    void putShortUnchecked(int n, short s) {
        if (this.memoryRef.isAccessible) {
            this.putShort(this.ix(n), s);
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    void putUnchecked(int n, char[] arrc, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.pokeCharArray((long)this.ix(n), (char[])arrc, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void putUnchecked(int n, double[] arrd, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.pokeDoubleArray((long)this.ix(n), (double[])arrd, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void putUnchecked(int n, float[] arrf, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.pokeFloatArray((long)this.ix(n), (float[])arrf, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void putUnchecked(int n, int[] arrn, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.pokeIntArray((long)this.ix(n), (int[])arrn, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    final void putUnchecked(int n, long[] arrl, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.pokeLongArray((long)this.ix(n), (long[])arrl, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    void putUnchecked(int n, short[] arrs, int n2, int n3) {
        if (this.memoryRef.isAccessible) {
            Memory.pokeShortArray((long)this.ix(n), (short[])arrs, (int)n2, (int)n3, (boolean)(this.nativeByteOrder ^ true));
            return;
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    @Override
    public final void setAccessible(boolean bl) {
        this.memoryRef.isAccessible = bl;
    }

    @Override
    public final ByteBuffer slice() {
        if (this.memoryRef.isAccessible) {
            int n;
            int n2 = this.position();
            n = n2 <= (n = this.limit()) ? (n -= n2) : 0;
            int n3 = this.offset;
            return new DirectByteBuffer(this.memoryRef, -1, 0, n, n, n3 + n2, this.isReadOnly);
        }
        throw new IllegalStateException("buffer is inaccessible");
    }

    static final class MemoryRef {
        long allocatedAddress;
        byte[] buffer;
        boolean isAccessible;
        boolean isFreed;
        final int offset;
        final Object originalBufferObject;

        MemoryRef(int n) {
            VMRuntime vMRuntime = VMRuntime.getRuntime();
            this.buffer = (byte[])vMRuntime.newNonMovableArray(Byte.TYPE, n + 7);
            long l = this.allocatedAddress = vMRuntime.addressOf((Object)this.buffer);
            this.offset = (int)((7L + l & -8L) - l);
            this.isAccessible = true;
            this.isFreed = false;
            this.originalBufferObject = null;
        }

        MemoryRef(long l, Object object) {
            this.buffer = null;
            this.allocatedAddress = l;
            this.offset = 0;
            this.originalBufferObject = object;
            this.isAccessible = true;
        }

        void free() {
            this.buffer = null;
            this.allocatedAddress = 0L;
            this.isAccessible = false;
            this.isFreed = true;
        }
    }

}

