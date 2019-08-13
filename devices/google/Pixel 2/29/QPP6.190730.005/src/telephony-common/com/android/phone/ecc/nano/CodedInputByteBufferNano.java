/*
 * Decompiled with CFR 0.145.
 */
package com.android.phone.ecc.nano;

import com.android.phone.ecc.nano.InternalNano;
import com.android.phone.ecc.nano.InvalidProtocolBufferNanoException;
import com.android.phone.ecc.nano.MessageNano;
import com.android.phone.ecc.nano.WireFormatNano;
import java.io.IOException;
import java.nio.charset.Charset;

public final class CodedInputByteBufferNano {
    private static final int DEFAULT_RECURSION_LIMIT = 64;
    private static final int DEFAULT_SIZE_LIMIT = 67108864;
    private final byte[] buffer;
    private int bufferPos;
    private int bufferSize;
    private int bufferSizeAfterLimit;
    private int bufferStart;
    private int currentLimit = Integer.MAX_VALUE;
    private int lastTag;
    private int recursionDepth;
    private int recursionLimit = 64;
    private int sizeLimit = 67108864;

    private CodedInputByteBufferNano(byte[] arrby, int n, int n2) {
        this.buffer = arrby;
        this.bufferStart = n;
        this.bufferSize = n + n2;
        this.bufferPos = n;
    }

    public static int decodeZigZag32(int n) {
        return n >>> 1 ^ -(n & 1);
    }

    public static long decodeZigZag64(long l) {
        return l >>> 1 ^ -(1L & l);
    }

    public static CodedInputByteBufferNano newInstance(byte[] arrby) {
        return CodedInputByteBufferNano.newInstance(arrby, 0, arrby.length);
    }

    public static CodedInputByteBufferNano newInstance(byte[] arrby, int n, int n2) {
        return new CodedInputByteBufferNano(arrby, n, n2);
    }

    private void recomputeBufferSizeAfterLimit() {
        this.bufferSize += this.bufferSizeAfterLimit;
        int n = this.bufferSize;
        int n2 = this.currentLimit;
        if (n > n2) {
            this.bufferSizeAfterLimit = n - n2;
            this.bufferSize -= this.bufferSizeAfterLimit;
        } else {
            this.bufferSizeAfterLimit = 0;
        }
    }

    public void checkLastTagWas(int n) throws InvalidProtocolBufferNanoException {
        if (this.lastTag == n) {
            return;
        }
        throw InvalidProtocolBufferNanoException.invalidEndTag();
    }

    public int getAbsolutePosition() {
        return this.bufferPos;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public int getBytesUntilLimit() {
        int n = this.currentLimit;
        if (n == Integer.MAX_VALUE) {
            return -1;
        }
        return n - this.bufferPos;
    }

    public byte[] getData(int n, int n2) {
        if (n2 == 0) {
            return WireFormatNano.EMPTY_BYTES;
        }
        byte[] arrby = new byte[n2];
        int n3 = this.bufferStart;
        System.arraycopy(this.buffer, n3 + n, arrby, 0, n2);
        return arrby;
    }

    public int getPosition() {
        return this.bufferPos - this.bufferStart;
    }

    public boolean isAtEnd() {
        boolean bl = this.bufferPos == this.bufferSize;
        return bl;
    }

    public void popLimit(int n) {
        this.currentLimit = n;
        this.recomputeBufferSizeAfterLimit();
    }

    public int pushLimit(int n) throws InvalidProtocolBufferNanoException {
        if (n >= 0) {
            int n2 = n + this.bufferPos;
            n = this.currentLimit;
            if (n2 <= n) {
                this.currentLimit = n2;
                this.recomputeBufferSizeAfterLimit();
                return n;
            }
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        throw InvalidProtocolBufferNanoException.negativeSize();
    }

    public boolean readBool() throws IOException {
        boolean bl = this.readRawVarint32() != 0;
        return bl;
    }

    public byte[] readBytes() throws IOException {
        int n;
        int n2;
        int n3 = this.readRawVarint32();
        if (n3 <= (n = this.bufferSize) - (n2 = this.bufferPos) && n3 > 0) {
            byte[] arrby = new byte[n3];
            System.arraycopy(this.buffer, n2, arrby, 0, n3);
            this.bufferPos += n3;
            return arrby;
        }
        if (n3 == 0) {
            return WireFormatNano.EMPTY_BYTES;
        }
        return this.readRawBytes(n3);
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readRawLittleEndian64());
    }

    public int readEnum() throws IOException {
        return this.readRawVarint32();
    }

    public int readFixed32() throws IOException {
        return this.readRawLittleEndian32();
    }

    public long readFixed64() throws IOException {
        return this.readRawLittleEndian64();
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readRawLittleEndian32());
    }

    public void readGroup(MessageNano messageNano, int n) throws IOException {
        int n2 = this.recursionDepth;
        if (n2 < this.recursionLimit) {
            this.recursionDepth = n2 + 1;
            messageNano.mergeFrom(this);
            this.checkLastTagWas(WireFormatNano.makeTag(n, 4));
            --this.recursionDepth;
            return;
        }
        throw InvalidProtocolBufferNanoException.recursionLimitExceeded();
    }

    public int readInt32() throws IOException {
        return this.readRawVarint32();
    }

    public long readInt64() throws IOException {
        return this.readRawVarint64();
    }

    public void readMessage(MessageNano messageNano) throws IOException {
        int n = this.readRawVarint32();
        if (this.recursionDepth < this.recursionLimit) {
            n = this.pushLimit(n);
            ++this.recursionDepth;
            messageNano.mergeFrom(this);
            this.checkLastTagWas(0);
            --this.recursionDepth;
            this.popLimit(n);
            return;
        }
        throw InvalidProtocolBufferNanoException.recursionLimitExceeded();
    }

    Object readPrimitiveField(int n) throws IOException {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown type ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 18: {
                return this.readSInt64();
            }
            case 17: {
                return this.readSInt32();
            }
            case 16: {
                return this.readSFixed64();
            }
            case 15: {
                return this.readSFixed32();
            }
            case 14: {
                return this.readEnum();
            }
            case 13: {
                return this.readUInt32();
            }
            case 12: {
                return this.readBytes();
            }
            case 9: {
                return this.readString();
            }
            case 8: {
                return this.readBool();
            }
            case 7: {
                return this.readFixed32();
            }
            case 6: {
                return this.readFixed64();
            }
            case 5: {
                return this.readInt32();
            }
            case 4: {
                return this.readUInt64();
            }
            case 3: {
                return this.readInt64();
            }
            case 2: {
                return Float.valueOf(this.readFloat());
            }
            case 1: 
        }
        return this.readDouble();
    }

    public byte readRawByte() throws IOException {
        int n = this.bufferPos;
        if (n != this.bufferSize) {
            byte[] arrby = this.buffer;
            this.bufferPos = n + 1;
            return arrby[n];
        }
        throw InvalidProtocolBufferNanoException.truncatedMessage();
    }

    public byte[] readRawBytes(int n) throws IOException {
        if (n >= 0) {
            int n2 = this.bufferPos;
            int n3 = this.currentLimit;
            if (n2 + n <= n3) {
                if (n <= this.bufferSize - n2) {
                    byte[] arrby = new byte[n];
                    System.arraycopy(this.buffer, n2, arrby, 0, n);
                    this.bufferPos += n;
                    return arrby;
                }
                throw InvalidProtocolBufferNanoException.truncatedMessage();
            }
            this.skipRawBytes(n3 - n2);
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        throw InvalidProtocolBufferNanoException.negativeSize();
    }

    public int readRawLittleEndian32() throws IOException {
        return this.readRawByte() & 255 | (this.readRawByte() & 255) << 8 | (this.readRawByte() & 255) << 16 | (this.readRawByte() & 255) << 24;
    }

    public long readRawLittleEndian64() throws IOException {
        byte by = this.readRawByte();
        byte by2 = this.readRawByte();
        byte by3 = this.readRawByte();
        byte by4 = this.readRawByte();
        byte by5 = this.readRawByte();
        byte by6 = this.readRawByte();
        byte by7 = this.readRawByte();
        byte by8 = this.readRawByte();
        return (long)by & 255L | ((long)by2 & 255L) << 8 | ((long)by3 & 255L) << 16 | ((long)by4 & 255L) << 24 | ((long)by5 & 255L) << 32 | ((long)by6 & 255L) << 40 | ((long)by7 & 255L) << 48 | (255L & (long)by8) << 56;
    }

    public int readRawVarint32() throws IOException {
        int n = this.readRawByte();
        if (n >= 0) {
            return n;
        }
        n &= 127;
        int n2 = this.readRawByte();
        if (n2 >= 0) {
            n |= n2 << 7;
        } else {
            n2 = n | (n2 & 127) << 7;
            n = this.readRawByte();
            if (n >= 0) {
                n = n2 | n << 14;
            } else {
                n2 |= (n & 127) << 14;
                n = this.readRawByte();
                if (n >= 0) {
                    n = n2 | n << 21;
                } else {
                    byte by = this.readRawByte();
                    n = n2 = n2 | (n & 127) << 21 | by << 28;
                    if (by < 0) {
                        for (n = 0; n < 5; ++n) {
                            if (this.readRawByte() < 0) continue;
                            return n2;
                        }
                        throw InvalidProtocolBufferNanoException.malformedVarint();
                    }
                }
            }
        }
        return n;
    }

    public long readRawVarint64() throws IOException {
        long l = 0L;
        for (int i = 0; i < 64; i += 7) {
            byte by = this.readRawByte();
            l |= (long)(by & 127) << i;
            if ((by & 128) != 0) continue;
            return l;
        }
        throw InvalidProtocolBufferNanoException.malformedVarint();
    }

    public int readSFixed32() throws IOException {
        return this.readRawLittleEndian32();
    }

    public long readSFixed64() throws IOException {
        return this.readRawLittleEndian64();
    }

    public int readSInt32() throws IOException {
        return CodedInputByteBufferNano.decodeZigZag32(this.readRawVarint32());
    }

    public long readSInt64() throws IOException {
        return CodedInputByteBufferNano.decodeZigZag64(this.readRawVarint64());
    }

    public String readString() throws IOException {
        int n;
        int n2;
        int n3 = this.readRawVarint32();
        if (n3 <= (n = this.bufferSize) - (n2 = this.bufferPos) && n3 > 0) {
            String string = new String(this.buffer, n2, n3, InternalNano.UTF_8);
            this.bufferPos += n3;
            return string;
        }
        return new String(this.readRawBytes(n3), InternalNano.UTF_8);
    }

    public int readTag() throws IOException {
        if (this.isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = this.readRawVarint32();
        int n = this.lastTag;
        if (n != 0) {
            return n;
        }
        throw InvalidProtocolBufferNanoException.invalidTag();
    }

    public int readUInt32() throws IOException {
        return this.readRawVarint32();
    }

    public long readUInt64() throws IOException {
        return this.readRawVarint64();
    }

    public void resetSizeCounter() {
    }

    public void rewindToPosition(int n) {
        int n2 = this.bufferPos;
        int n3 = this.bufferStart;
        if (n <= n2 - n3) {
            if (n >= 0) {
                this.bufferPos = n3 + n;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad position ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Position ");
        stringBuilder.append(n);
        stringBuilder.append(" is beyond current ");
        stringBuilder.append(this.bufferPos - this.bufferStart);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int setRecursionLimit(int n) {
        if (n >= 0) {
            int n2 = this.recursionLimit;
            this.recursionLimit = n;
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Recursion limit cannot be negative: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int setSizeLimit(int n) {
        if (n >= 0) {
            int n2 = this.sizeLimit;
            this.sizeLimit = n;
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Size limit cannot be negative: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean skipField(int n) throws IOException {
        int n2 = WireFormatNano.getTagWireType(n);
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 != 4) {
                            if (n2 == 5) {
                                this.readRawLittleEndian32();
                                return true;
                            }
                            throw InvalidProtocolBufferNanoException.invalidWireType();
                        }
                        return false;
                    }
                    this.skipMessage();
                    this.checkLastTagWas(WireFormatNano.makeTag(WireFormatNano.getTagFieldNumber(n), 4));
                    return true;
                }
                this.skipRawBytes(this.readRawVarint32());
                return true;
            }
            this.readRawLittleEndian64();
            return true;
        }
        this.readInt32();
        return true;
    }

    public void skipMessage() throws IOException {
        int n;
        while ((n = this.readTag()) != 0 && this.skipField(n)) {
        }
    }

    public void skipRawBytes(int n) throws IOException {
        if (n >= 0) {
            int n2 = this.bufferPos;
            int n3 = this.currentLimit;
            if (n2 + n <= n3) {
                if (n <= this.bufferSize - n2) {
                    this.bufferPos = n2 + n;
                    return;
                }
                throw InvalidProtocolBufferNanoException.truncatedMessage();
            }
            this.skipRawBytes(n3 - n2);
            throw InvalidProtocolBufferNanoException.truncatedMessage();
        }
        throw InvalidProtocolBufferNanoException.negativeSize();
    }
}

