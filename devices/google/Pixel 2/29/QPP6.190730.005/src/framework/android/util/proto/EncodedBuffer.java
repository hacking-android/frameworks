/*
 * Decompiled with CFR 0.145.
 */
package android.util.proto;

import android.util.Log;
import android.util.proto.ProtoParseException;
import java.util.ArrayList;

public final class EncodedBuffer {
    private static final String TAG = "EncodedBuffer";
    private int mBufferCount;
    private final ArrayList<byte[]> mBuffers = new ArrayList();
    private final int mChunkSize;
    private int mReadBufIndex;
    private byte[] mReadBuffer;
    private int mReadIndex;
    private int mReadLimit = -1;
    private int mReadableSize = -1;
    private int mWriteBufIndex;
    private byte[] mWriteBuffer;
    private int mWriteIndex;

    public EncodedBuffer() {
        this(0);
    }

    public EncodedBuffer(int n) {
        int n2 = n;
        if (n <= 0) {
            n2 = 8192;
        }
        this.mChunkSize = n2;
        this.mWriteBuffer = new byte[this.mChunkSize];
        this.mBuffers.add(this.mWriteBuffer);
        this.mBufferCount = 1;
    }

    private static int dumpByteString(String string2, String string3, int n, byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = arrby.length;
        for (int i = 0; i < n2; ++i) {
            StringBuffer stringBuffer2;
            if (i % 16 == 0) {
                stringBuffer2 = stringBuffer;
                if (i != 0) {
                    Log.d(string2, stringBuffer.toString());
                    stringBuffer2 = new StringBuffer();
                }
                stringBuffer2.append(string3);
                stringBuffer2.append('[');
                stringBuffer2.append(n + i);
                stringBuffer2.append(']');
                stringBuffer2.append(' ');
            } else {
                stringBuffer.append(' ');
                stringBuffer2 = stringBuffer;
            }
            byte by = arrby[i];
            byte by2 = (byte)(by >> 4 & 15);
            if (by2 < 10) {
                stringBuffer2.append((char)(by2 + 48));
            } else {
                stringBuffer2.append((char)(by2 + 87));
            }
            by2 = (byte)(by & 15);
            if (by2 < 10) {
                stringBuffer2.append((char)(by2 + 48));
            } else {
                stringBuffer2.append((char)(by2 + 87));
            }
            stringBuffer = stringBuffer2;
        }
        Log.d(string2, stringBuffer.toString());
        return n2;
    }

    public static void dumpByteString(String string2, String string3, byte[] arrby) {
        EncodedBuffer.dumpByteString(string2, string3, 0, arrby);
    }

    public static int getRawVarint32Size(int n) {
        if ((n & -128) == 0) {
            return 1;
        }
        if ((n & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & n) == 0) {
            return 3;
        }
        if ((-268435456 & n) == 0) {
            return 4;
        }
        return 5;
    }

    public static int getRawVarint64Size(long l) {
        if ((-128L & l) == 0L) {
            return 1;
        }
        if ((-16384L & l) == 0L) {
            return 2;
        }
        if ((-2097152L & l) == 0L) {
            return 3;
        }
        if ((-268435456L & l) == 0L) {
            return 4;
        }
        if ((-34359738368L & l) == 0L) {
            return 5;
        }
        if ((-4398046511104L & l) == 0L) {
            return 6;
        }
        if ((-562949953421312L & l) == 0L) {
            return 7;
        }
        if ((-72057594037927936L & l) == 0L) {
            return 8;
        }
        if ((Long.MIN_VALUE & l) == 0L) {
            return 9;
        }
        return 10;
    }

    public static int getRawZigZag32Size(int n) {
        return EncodedBuffer.getRawVarint32Size(EncodedBuffer.zigZag32(n));
    }

    public static int getRawZigZag64Size(long l) {
        return EncodedBuffer.getRawVarint64Size(EncodedBuffer.zigZag64(l));
    }

    private void nextWriteBuffer() {
        ++this.mWriteBufIndex;
        int n = this.mWriteBufIndex;
        if (n >= this.mBufferCount) {
            this.mWriteBuffer = new byte[this.mChunkSize];
            this.mBuffers.add(this.mWriteBuffer);
            ++this.mBufferCount;
        } else {
            this.mWriteBuffer = this.mBuffers.get(n);
        }
        this.mWriteIndex = 0;
    }

    private static int zigZag32(int n) {
        return n << 1 ^ n >> 31;
    }

    private static long zigZag64(long l) {
        return l << 1 ^ l >> 63;
    }

    public void dumpBuffers(String string2) {
        int n = this.mBuffers.size();
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            stringBuilder.append(i);
            stringBuilder.append("} ");
            n2 += EncodedBuffer.dumpByteString(string2, stringBuilder.toString(), n2, this.mBuffers.get(i));
        }
    }

    public void editRawFixed32(int n, int n2) {
        byte[] arrby = this.mBuffers.get(n / this.mChunkSize);
        int n3 = this.mChunkSize;
        arrby[n % n3] = (byte)n2;
        arrby = this.mBuffers.get((n + 1) / n3);
        n3 = this.mChunkSize;
        arrby[(n + 1) % n3] = (byte)(n2 >> 8);
        arrby = this.mBuffers.get((n + 2) / n3);
        n3 = this.mChunkSize;
        arrby[(n + 2) % n3] = (byte)(n2 >> 16);
        this.mBuffers.get((int)((n + 3) / n3))[(n + 3) % this.mChunkSize] = (byte)(n2 >> 24);
    }

    public byte[] getBytes(int n) {
        int n2;
        byte[] arrby = new byte[n];
        int n3 = n / this.mChunkSize;
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            System.arraycopy(this.mBuffers.get(n2), 0, arrby, n4, this.mChunkSize);
            n4 += this.mChunkSize;
        }
        if ((n -= this.mChunkSize * n3) > 0) {
            System.arraycopy(this.mBuffers.get(n2), 0, arrby, n4, n);
        }
        return arrby;
    }

    public int getChunkCount() {
        return this.mBuffers.size();
    }

    public String getDebugString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EncodedBuffer( mChunkSize=");
        stringBuilder.append(this.mChunkSize);
        stringBuilder.append(" mBuffers.size=");
        stringBuilder.append(this.mBuffers.size());
        stringBuilder.append(" mBufferCount=");
        stringBuilder.append(this.mBufferCount);
        stringBuilder.append(" mWriteIndex=");
        stringBuilder.append(this.mWriteIndex);
        stringBuilder.append(" mWriteBufIndex=");
        stringBuilder.append(this.mWriteBufIndex);
        stringBuilder.append(" mReadBufIndex=");
        stringBuilder.append(this.mReadBufIndex);
        stringBuilder.append(" mReadIndex=");
        stringBuilder.append(this.mReadIndex);
        stringBuilder.append(" mReadableSize=");
        stringBuilder.append(this.mReadableSize);
        stringBuilder.append(" mReadLimit=");
        stringBuilder.append(this.mReadLimit);
        stringBuilder.append(" )");
        return stringBuilder.toString();
    }

    public int getRawFixed32At(int n) {
        byte[] arrby = this.mBuffers.get(n / this.mChunkSize);
        int n2 = this.mChunkSize;
        byte by = arrby[n % n2];
        arrby = this.mBuffers.get((n + 1) / n2);
        int n3 = this.mChunkSize;
        n2 = arrby[(n + 1) % n3];
        arrby = this.mBuffers.get((n + 2) / n3);
        n3 = this.mChunkSize;
        return by & 255 | (n2 & 255) << 8 | (arrby[(n + 2) % n3] & 255) << 16 | (this.mBuffers.get((n + 3) / n3)[(n + 3) % this.mChunkSize] & 255) << 24;
    }

    public int getReadPos() {
        return this.mReadBufIndex * this.mChunkSize + this.mReadIndex;
    }

    public int getReadableSize() {
        return this.mReadableSize;
    }

    public int getSize() {
        return (this.mBufferCount - 1) * this.mChunkSize + this.mWriteIndex;
    }

    public int getWriteBufIndex() {
        return this.mWriteBufIndex;
    }

    public int getWriteIndex() {
        return this.mWriteIndex;
    }

    public int getWritePos() {
        return this.mWriteBufIndex * this.mChunkSize + this.mWriteIndex;
    }

    public byte readRawByte() {
        int n = this.mReadBufIndex;
        int n2 = this.mBufferCount;
        if (n <= n2 && (n != n2 - 1 || this.mReadIndex < this.mReadLimit)) {
            if (this.mReadIndex >= this.mChunkSize) {
                ++this.mReadBufIndex;
                this.mReadBuffer = this.mBuffers.get(this.mReadBufIndex);
                this.mReadIndex = 0;
            }
            byte[] arrby = this.mReadBuffer;
            n2 = this.mReadIndex;
            this.mReadIndex = n2 + 1;
            return arrby[n2];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Trying to read too much data mReadBufIndex=");
        stringBuilder.append(this.mReadBufIndex);
        stringBuilder.append(" mBufferCount=");
        stringBuilder.append(this.mBufferCount);
        stringBuilder.append(" mReadIndex=");
        stringBuilder.append(this.mReadIndex);
        stringBuilder.append(" mReadLimit=");
        stringBuilder.append(this.mReadLimit);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int readRawFixed32() {
        return this.readRawByte() & 255 | (this.readRawByte() & 255) << 8 | (this.readRawByte() & 255) << 16 | (this.readRawByte() & 255) << 24;
    }

    public long readRawUnsigned() {
        int n = 0;
        long l = 0L;
        do {
            byte by = this.readRawByte();
            l |= (long)(by & 127) << n;
            if ((by & 128) != 0) continue;
            return l;
        } while ((n += 7) <= 64);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Varint too long -- ");
        stringBuilder.append(this.getDebugString());
        throw new ProtoParseException(stringBuilder.toString());
    }

    public void rewindRead() {
        this.mReadBuffer = this.mBuffers.get(0);
        this.mReadBufIndex = 0;
        this.mReadIndex = 0;
    }

    public void rewindWriteTo(int n) {
        if (n <= this.getWritePos()) {
            int n2 = this.mChunkSize;
            this.mWriteBufIndex = n / n2;
            this.mWriteIndex = n % n2;
            if (this.mWriteIndex == 0 && (n = this.mWriteBufIndex) != 0) {
                this.mWriteIndex = n2;
                this.mWriteBufIndex = n - 1;
            }
            this.mWriteBuffer = this.mBuffers.get(this.mWriteBufIndex);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("rewindWriteTo only can go backwards");
        stringBuilder.append(n);
        throw new RuntimeException(stringBuilder.toString());
    }

    public void skipRead(int n) {
        if (n >= 0) {
            if (n == 0) {
                return;
            }
            int n2 = this.mChunkSize;
            int n3 = this.mReadIndex;
            if (n <= n2 - n3) {
                this.mReadIndex = n3 + n;
            } else {
                this.mReadIndex = (n -= n2 - n3) % n2;
                if (this.mReadIndex == 0) {
                    this.mReadIndex = n2;
                    this.mReadBufIndex += n / n2;
                } else {
                    this.mReadBufIndex += n / n2 + 1;
                }
                this.mReadBuffer = this.mBuffers.get(this.mReadBufIndex);
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("skipRead with negative amount=");
        stringBuilder.append(n);
        throw new RuntimeException(stringBuilder.toString());
    }

    public void startEditing() {
        int n = this.mWriteBufIndex;
        int n2 = this.mChunkSize;
        int n3 = this.mWriteIndex;
        this.mReadableSize = n * n2 + n3;
        this.mReadLimit = n3;
        this.mWriteBuffer = this.mBuffers.get(0);
        this.mWriteIndex = 0;
        this.mWriteBufIndex = 0;
        this.mReadBuffer = this.mWriteBuffer;
        this.mReadBufIndex = 0;
        this.mReadIndex = 0;
    }

    public void writeFromThisBuffer(int n, int n2) {
        if (this.mReadLimit >= 0) {
            if (n >= this.getWritePos()) {
                if (n + n2 <= this.mReadableSize) {
                    if (n2 == 0) {
                        return;
                    }
                    int n3 = this.mWriteBufIndex;
                    int n4 = this.mChunkSize;
                    int n5 = this.mWriteIndex;
                    if (n == n3 * n4 + n5) {
                        if (n2 <= n4 - n5) {
                            this.mWriteIndex = n5 + n2;
                        } else {
                            n = n2 - (n4 - n5);
                            this.mWriteIndex = n % n4;
                            if (this.mWriteIndex == 0) {
                                this.mWriteIndex = n4;
                                this.mWriteBufIndex = n3 + n / n4;
                            } else {
                                this.mWriteBufIndex = n3 + (n / n4 + 1);
                            }
                            this.mWriteBuffer = this.mBuffers.get(this.mWriteBufIndex);
                        }
                    } else {
                        n5 = n / n4;
                        byte[] arrby = this.mBuffers.get(n5);
                        n %= this.mChunkSize;
                        while (n2 > 0) {
                            if (this.mWriteIndex >= this.mChunkSize) {
                                this.nextWriteBuffer();
                            }
                            n3 = n5;
                            n4 = n;
                            if (n >= this.mChunkSize) {
                                n3 = n5 + 1;
                                arrby = this.mBuffers.get(n3);
                                n4 = 0;
                            }
                            n = this.mChunkSize;
                            n5 = Math.min(n2, Math.min(n - this.mWriteIndex, n - n4));
                            System.arraycopy(arrby, n4, this.mWriteBuffer, this.mWriteIndex, n5);
                            this.mWriteIndex += n5;
                            n = n4 + n5;
                            n2 -= n5;
                            n5 = n3;
                        }
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Trying to move more data than there is -- srcOffset=");
                stringBuilder.append(n);
                stringBuilder.append(" size=");
                stringBuilder.append(n2);
                stringBuilder.append(" ");
                stringBuilder.append(this.getDebugString());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can only move forward in the buffer -- srcOffset=");
            stringBuilder.append(n);
            stringBuilder.append(" size=");
            stringBuilder.append(n2);
            stringBuilder.append(" ");
            stringBuilder.append(this.getDebugString());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        throw new IllegalStateException("writeFromThisBuffer before startEditing");
    }

    public void writeRawBuffer(byte[] arrby) {
        if (arrby != null && arrby.length > 0) {
            this.writeRawBuffer(arrby, 0, arrby.length);
        }
    }

    public void writeRawBuffer(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            return;
        }
        int n3 = this.mChunkSize;
        int n4 = this.mWriteIndex;
        int n5 = n2 < n3 - n4 ? n2 : n3 - n4;
        n3 = n;
        n4 = n2;
        if (n5 > 0) {
            System.arraycopy(arrby, n, this.mWriteBuffer, this.mWriteIndex, n5);
            this.mWriteIndex += n5;
            n4 = n2 - n5;
            n3 = n + n5;
        }
        while (n4 > 0) {
            this.nextWriteBuffer();
            n = n2 = this.mChunkSize;
            if (n4 < n2) {
                n = n4;
            }
            System.arraycopy(arrby, n3, this.mWriteBuffer, this.mWriteIndex, n);
            this.mWriteIndex += n;
            n4 -= n;
            n3 += n;
        }
    }

    public void writeRawByte(byte by) {
        if (this.mWriteIndex >= this.mChunkSize) {
            this.nextWriteBuffer();
        }
        byte[] arrby = this.mWriteBuffer;
        int n = this.mWriteIndex;
        this.mWriteIndex = n + 1;
        arrby[n] = by;
    }

    public void writeRawFixed32(int n) {
        this.writeRawByte((byte)n);
        this.writeRawByte((byte)(n >> 8));
        this.writeRawByte((byte)(n >> 16));
        this.writeRawByte((byte)(n >> 24));
    }

    public void writeRawFixed64(long l) {
        this.writeRawByte((byte)l);
        this.writeRawByte((byte)(l >> 8));
        this.writeRawByte((byte)(l >> 16));
        this.writeRawByte((byte)(l >> 24));
        this.writeRawByte((byte)(l >> 32));
        this.writeRawByte((byte)(l >> 40));
        this.writeRawByte((byte)(l >> 48));
        this.writeRawByte((byte)(l >> 56));
    }

    public void writeRawVarint32(int n) {
        do {
            if ((n & -128) == 0) {
                this.writeRawByte((byte)n);
                return;
            }
            this.writeRawByte((byte)(n & 127 | 128));
            n >>>= 7;
        } while (true);
    }

    public void writeRawVarint64(long l) {
        do {
            if ((-128L & l) == 0L) {
                this.writeRawByte((byte)l);
                return;
            }
            this.writeRawByte((byte)(127L & l | 128L));
            l >>>= 7;
        } while (true);
    }

    public void writeRawZigZag32(int n) {
        this.writeRawVarint32(EncodedBuffer.zigZag32(n));
    }

    public void writeRawZigZag64(long l) {
        this.writeRawVarint64(EncodedBuffer.zigZag64(l));
    }
}

