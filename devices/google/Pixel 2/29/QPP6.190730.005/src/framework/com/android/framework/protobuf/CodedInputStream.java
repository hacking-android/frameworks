/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedOutputStream;
import com.android.framework.protobuf.ExtensionRegistryLite;
import com.android.framework.protobuf.Internal;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.Parser;
import com.android.framework.protobuf.Utf8;
import com.android.framework.protobuf.WireFormat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

public final class CodedInputStream {
    private static final int BUFFER_SIZE = 4096;
    private static final int DEFAULT_RECURSION_LIMIT = 100;
    private static final int DEFAULT_SIZE_LIMIT = 67108864;
    private final byte[] buffer;
    private final boolean bufferIsImmutable;
    private int bufferPos;
    private int bufferSize;
    private int bufferSizeAfterLimit;
    private int currentLimit = Integer.MAX_VALUE;
    private boolean enableAliasing = false;
    private final InputStream input;
    private int lastTag;
    private int recursionDepth;
    private int recursionLimit = 100;
    private RefillCallback refillCallback = null;
    private int sizeLimit = 67108864;
    private int totalBytesRetired;

    private CodedInputStream(InputStream inputStream, int n) {
        this.buffer = new byte[n];
        this.bufferPos = 0;
        this.totalBytesRetired = 0;
        this.input = inputStream;
        this.bufferIsImmutable = false;
    }

    private CodedInputStream(byte[] arrby, int n, int n2, boolean bl) {
        this.buffer = arrby;
        this.bufferSize = n + n2;
        this.bufferPos = n;
        this.totalBytesRetired = -n;
        this.input = null;
        this.bufferIsImmutable = bl;
    }

    public static int decodeZigZag32(int n) {
        return n >>> 1 ^ -(n & 1);
    }

    public static long decodeZigZag64(long l) {
        return l >>> 1 ^ -(1L & l);
    }

    public static CodedInputStream newInstance(InputStream inputStream) {
        return new CodedInputStream(inputStream, 4096);
    }

    static CodedInputStream newInstance(InputStream inputStream, int n) {
        return new CodedInputStream(inputStream, n);
    }

    public static CodedInputStream newInstance(ByteBuffer arrby) {
        if (arrby.hasArray()) {
            return CodedInputStream.newInstance(arrby.array(), arrby.arrayOffset() + arrby.position(), arrby.remaining());
        }
        ByteBuffer byteBuffer = arrby.duplicate();
        arrby = new byte[byteBuffer.remaining()];
        byteBuffer.get(arrby);
        return CodedInputStream.newInstance(arrby);
    }

    public static CodedInputStream newInstance(byte[] arrby) {
        return CodedInputStream.newInstance(arrby, 0, arrby.length);
    }

    public static CodedInputStream newInstance(byte[] arrby, int n, int n2) {
        return CodedInputStream.newInstance(arrby, n, n2, false);
    }

    static CodedInputStream newInstance(byte[] object, int n, int n2, boolean bl) {
        object = new CodedInputStream((byte[])object, n, n2, bl);
        try {
            ((CodedInputStream)object).pushLimit(n2);
            return object;
        }
        catch (InvalidProtocolBufferException invalidProtocolBufferException) {
            throw new IllegalArgumentException(invalidProtocolBufferException);
        }
    }

    private byte[] readRawBytesSlowPath(int n) throws IOException {
        if (n <= 0) {
            if (n == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }
        int n2 = this.totalBytesRetired;
        int n3 = this.bufferPos;
        int n4 = n2 + n3 + n;
        if (n4 <= this.sizeLimit) {
            int n5 = this.currentLimit;
            if (n4 <= n5) {
                byte[] arrby = this.input;
                if (arrby != null) {
                    n4 = this.bufferPos;
                    n5 = this.bufferSize;
                    this.totalBytesRetired = n2 + n5;
                    this.bufferPos = 0;
                    this.bufferSize = 0;
                    n2 = n - n3;
                    if (n2 >= 4096 && n2 > arrby.available()) {
                        ArrayList<byte[]> arrby22 = new ArrayList<byte[]>();
                        while (n2 > 0) {
                            int n6;
                            arrby = new byte[Math.min(n2, 4096)];
                            for (n5 = 0; n5 < arrby.length; n5 += n6) {
                                n6 = this.input.read(arrby, n5, arrby.length - n5);
                                if (n6 != -1) {
                                    this.totalBytesRetired += n6;
                                    continue;
                                }
                                throw InvalidProtocolBufferException.truncatedMessage();
                            }
                            n2 -= arrby.length;
                            arrby22.add(arrby);
                        }
                        arrby = new byte[n];
                        System.arraycopy(this.buffer, n4, arrby, 0, n3);
                        n = n3;
                        for (byte[] arrby2 : arrby22) {
                            System.arraycopy(arrby2, 0, arrby, n, arrby2.length);
                            n += arrby2.length;
                        }
                        return arrby;
                    }
                    arrby = new byte[n];
                    System.arraycopy(this.buffer, n4, arrby, 0, n3);
                    for (n3 = n5 - n3; n3 < arrby.length; n3 += n2) {
                        n2 = this.input.read(arrby, n3, n - n3);
                        if (n2 != -1) {
                            this.totalBytesRetired += n2;
                            continue;
                        }
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    return arrby;
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            this.skipRawBytes(n5 - n2 - n3);
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        throw InvalidProtocolBufferException.sizeLimitExceeded();
    }

    public static int readRawVarint32(int n, InputStream inputStream) throws IOException {
        int n2;
        int n3;
        block5 : {
            if ((n & 128) == 0) {
                return n;
            }
            n2 = n & 127;
            n = 7;
            do {
                if (n >= 32) break block5;
                n3 = inputStream.read();
                if (n3 == -1) break;
                n2 |= (n3 & 127) << n;
                if ((n3 & 128) == 0) {
                    return n2;
                }
                n += 7;
            } while (true);
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        for (n3 = n; n3 < 64; n3 += 7) {
            n = inputStream.read();
            if (n != -1) {
                if ((n & 128) != 0) continue;
                return n2;
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    static int readRawVarint32(InputStream inputStream) throws IOException {
        int n = inputStream.read();
        if (n != -1) {
            return CodedInputStream.readRawVarint32(n, inputStream);
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    private void recomputeBufferSizeAfterLimit() {
        this.bufferSize += this.bufferSizeAfterLimit;
        int n = this.totalBytesRetired;
        int n2 = this.bufferSize;
        int n3 = this.currentLimit;
        if ((n += n2) > n3) {
            this.bufferSizeAfterLimit = n - n3;
            this.bufferSize = n2 - this.bufferSizeAfterLimit;
        } else {
            this.bufferSizeAfterLimit = 0;
        }
    }

    private void refillBuffer(int n) throws IOException {
        if (this.tryRefillBuffer(n)) {
            return;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    private void skipRawBytesSlowPath(int n) throws IOException {
        if (n >= 0) {
            int n2 = this.totalBytesRetired;
            int n3 = this.bufferPos;
            int n4 = this.currentLimit;
            if (n2 + n3 + n <= n4) {
                n2 = this.bufferSize;
                n3 = n2 - n3;
                this.bufferPos = n2;
                this.refillBuffer(1);
                while (n - n3 > (n2 = this.bufferSize)) {
                    n3 += n2;
                    this.bufferPos = n2;
                    this.refillBuffer(1);
                }
                this.bufferPos = n - n3;
                return;
            }
            this.skipRawBytes(n4 - n2 - n3);
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        throw InvalidProtocolBufferException.negativeSize();
    }

    private void skipRawVarint() throws IOException {
        if (this.bufferSize - this.bufferPos >= 10) {
            byte[] arrby = this.buffer;
            int n = this.bufferPos;
            for (int i = 0; i < 10; ++i) {
                int n2 = n + 1;
                if (arrby[n] >= 0) {
                    this.bufferPos = n2;
                    return;
                }
                n = n2;
            }
        }
        this.skipRawVarintSlowPath();
    }

    private void skipRawVarintSlowPath() throws IOException {
        for (int i = 0; i < 10; ++i) {
            if (this.readRawByte() < 0) continue;
            return;
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    private boolean tryRefillBuffer(int n) throws IOException {
        int n2 = this.bufferPos;
        if (n2 + n > this.bufferSize) {
            if (this.totalBytesRetired + n2 + n > this.currentLimit) {
                return false;
            }
            Object object = this.refillCallback;
            if (object != null) {
                object.onRefill();
            }
            if (this.input != null) {
                int n3 = this.bufferPos;
                if (n3 > 0) {
                    n2 = this.bufferSize;
                    if (n2 > n3) {
                        object = this.buffer;
                        System.arraycopy(object, n3, object, 0, n2 - n3);
                    }
                    this.totalBytesRetired += n3;
                    this.bufferSize -= n3;
                    this.bufferPos = 0;
                }
                InputStream inputStream = this.input;
                object = this.buffer;
                n2 = this.bufferSize;
                if ((n2 = inputStream.read((byte[])object, n2, ((byte[])object).length - n2)) != 0 && n2 >= -1 && n2 <= this.buffer.length) {
                    if (n2 > 0) {
                        this.bufferSize += n2;
                        if (this.totalBytesRetired + n - this.sizeLimit <= 0) {
                            this.recomputeBufferSizeAfterLimit();
                            boolean bl = this.bufferSize >= n ? true : this.tryRefillBuffer(n);
                            return bl;
                        }
                        throw InvalidProtocolBufferException.sizeLimitExceeded();
                    }
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("InputStream#read(byte[]) returned invalid result: ");
                    ((StringBuilder)object).append(n2);
                    ((StringBuilder)object).append("\nThe InputStream implementation is buggy.");
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
            }
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("refillBuffer() called when ");
        stringBuilder.append(n);
        stringBuilder.append(" bytes were already available in buffer");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void checkLastTagWas(int n) throws InvalidProtocolBufferException {
        if (this.lastTag == n) {
            return;
        }
        throw InvalidProtocolBufferException.invalidEndTag();
    }

    public void enableAliasing(boolean bl) {
        this.enableAliasing = bl;
    }

    public int getBytesUntilLimit() {
        int n = this.currentLimit;
        if (n == Integer.MAX_VALUE) {
            return -1;
        }
        return n - (this.totalBytesRetired + this.bufferPos);
    }

    public int getLastTag() {
        return this.lastTag;
    }

    public int getTotalBytesRead() {
        return this.totalBytesRetired + this.bufferPos;
    }

    public boolean isAtEnd() throws IOException {
        int n = this.bufferPos;
        int n2 = this.bufferSize;
        boolean bl = true;
        if (n != n2 || this.tryRefillBuffer(1)) {
            bl = false;
        }
        return bl;
    }

    public void popLimit(int n) {
        this.currentLimit = n;
        this.recomputeBufferSizeAfterLimit();
    }

    public int pushLimit(int n) throws InvalidProtocolBufferException {
        if (n >= 0) {
            int n2 = n + (this.totalBytesRetired + this.bufferPos);
            n = this.currentLimit;
            if (n2 <= n) {
                this.currentLimit = n2;
                this.recomputeBufferSizeAfterLimit();
                return n;
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        throw InvalidProtocolBufferException.negativeSize();
    }

    public boolean readBool() throws IOException {
        boolean bl = this.readRawVarint64() != 0L;
        return bl;
    }

    public byte[] readByteArray() throws IOException {
        int n;
        int n2;
        int n3 = this.readRawVarint32();
        if (n3 <= (n = this.bufferSize) - (n2 = this.bufferPos) && n3 > 0) {
            byte[] arrby = Arrays.copyOfRange(this.buffer, n2, n2 + n3);
            this.bufferPos += n3;
            return arrby;
        }
        return this.readRawBytesSlowPath(n3);
    }

    public ByteBuffer readByteBuffer() throws IOException {
        int n;
        int n2;
        int n3 = this.readRawVarint32();
        if (n3 <= (n = this.bufferSize) - (n2 = this.bufferPos) && n3 > 0) {
            Object object;
            if (this.input == null && !this.bufferIsImmutable && this.enableAliasing) {
                object = ByteBuffer.wrap(this.buffer, n2, n3).slice();
            } else {
                object = this.buffer;
                n = this.bufferPos;
                object = ByteBuffer.wrap(Arrays.copyOfRange(object, n, n + n3));
            }
            this.bufferPos += n3;
            return object;
        }
        if (n3 == 0) {
            return Internal.EMPTY_BYTE_BUFFER;
        }
        return ByteBuffer.wrap(this.readRawBytesSlowPath(n3));
    }

    public ByteString readBytes() throws IOException {
        int n;
        int n2;
        int n3 = this.readRawVarint32();
        if (n3 <= (n = this.bufferSize) - (n2 = this.bufferPos) && n3 > 0) {
            ByteString byteString = this.bufferIsImmutable && this.enableAliasing ? ByteString.wrap(this.buffer, n2, n3) : ByteString.copyFrom(this.buffer, this.bufferPos, n3);
            this.bufferPos += n3;
            return byteString;
        }
        if (n3 == 0) {
            return ByteString.EMPTY;
        }
        return ByteString.wrap(this.readRawBytesSlowPath(n3));
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

    public <T extends MessageLite> T readGroup(int n, Parser<T> object, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int n2 = this.recursionDepth;
        if (n2 < this.recursionLimit) {
            this.recursionDepth = n2 + 1;
            object = (MessageLite)object.parsePartialFrom(this, extensionRegistryLite);
            this.checkLastTagWas(WireFormat.makeTag(n, 4));
            --this.recursionDepth;
            return (T)object;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    public void readGroup(int n, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int n2 = this.recursionDepth;
        if (n2 < this.recursionLimit) {
            this.recursionDepth = n2 + 1;
            builder.mergeFrom(this, extensionRegistryLite);
            this.checkLastTagWas(WireFormat.makeTag(n, 4));
            --this.recursionDepth;
            return;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    public int readInt32() throws IOException {
        return this.readRawVarint32();
    }

    public long readInt64() throws IOException {
        return this.readRawVarint64();
    }

    public <T extends MessageLite> T readMessage(Parser<T> object, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int n = this.readRawVarint32();
        if (this.recursionDepth < this.recursionLimit) {
            n = this.pushLimit(n);
            ++this.recursionDepth;
            object = (MessageLite)object.parsePartialFrom(this, extensionRegistryLite);
            this.checkLastTagWas(0);
            --this.recursionDepth;
            this.popLimit(n);
            return (T)object;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int n = this.readRawVarint32();
        if (this.recursionDepth < this.recursionLimit) {
            n = this.pushLimit(n);
            ++this.recursionDepth;
            builder.mergeFrom(this, extensionRegistryLite);
            this.checkLastTagWas(0);
            --this.recursionDepth;
            this.popLimit(n);
            return;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    public byte readRawByte() throws IOException {
        if (this.bufferPos == this.bufferSize) {
            this.refillBuffer(1);
        }
        byte[] arrby = this.buffer;
        int n = this.bufferPos;
        this.bufferPos = n + 1;
        return arrby[n];
    }

    public byte[] readRawBytes(int n) throws IOException {
        int n2 = this.bufferPos;
        if (n <= this.bufferSize - n2 && n > 0) {
            this.bufferPos = n2 + n;
            return Arrays.copyOfRange(this.buffer, n2, n2 + n);
        }
        return this.readRawBytesSlowPath(n);
    }

    public int readRawLittleEndian32() throws IOException {
        int n;
        int n2 = n = this.bufferPos;
        if (this.bufferSize - n < 4) {
            this.refillBuffer(4);
            n2 = this.bufferPos;
        }
        byte[] arrby = this.buffer;
        this.bufferPos = n2 + 4;
        return arrby[n2] & 255 | (arrby[n2 + 1] & 255) << 8 | (arrby[n2 + 2] & 255) << 16 | (arrby[n2 + 3] & 255) << 24;
    }

    public long readRawLittleEndian64() throws IOException {
        int n;
        int n2 = n = this.bufferPos;
        if (this.bufferSize - n < 8) {
            this.refillBuffer(8);
            n2 = this.bufferPos;
        }
        byte[] arrby = this.buffer;
        this.bufferPos = n2 + 8;
        return (long)arrby[n2] & 255L | ((long)arrby[n2 + 1] & 255L) << 8 | ((long)arrby[n2 + 2] & 255L) << 16 | ((long)arrby[n2 + 3] & 255L) << 24 | ((long)arrby[n2 + 4] & 255L) << 32 | ((long)arrby[n2 + 5] & 255L) << 40 | ((long)arrby[n2 + 6] & 255L) << 48 | ((long)arrby[n2 + 7] & 255L) << 56;
    }

    public int readRawVarint32() throws IOException {
        int n;
        int n2;
        block6 : {
            int n3;
            block9 : {
                block4 : {
                    int n4;
                    byte[] arrby;
                    block8 : {
                        block7 : {
                            block5 : {
                                n = this.bufferSize;
                                n3 = this.bufferPos;
                                if (n == n3) break block4;
                                arrby = this.buffer;
                                n2 = n3 + 1;
                                if ((n3 = arrby[n3]) >= 0) {
                                    this.bufferPos = n2;
                                    return n3;
                                }
                                if (n - n2 < 9) break block4;
                                n = n2 + 1;
                                if ((n3 = arrby[n2] << 7 ^ n3) >= 0) break block5;
                                n2 = n3 ^ -128;
                                break block6;
                            }
                            n2 = n + 1;
                            if ((n3 = arrby[n] << 14 ^ n3) < 0) break block7;
                            n = n2;
                            n2 = n3 ^= 16256;
                            break block6;
                        }
                        n = n2 + 1;
                        if ((n2 = arrby[n2] << 21 ^ n3) >= 0) break block8;
                        n2 = -2080896 ^ n2;
                        break block6;
                    }
                    int n5 = n + 1;
                    int n6 = arrby[n];
                    n3 = n2 ^ n6 << 28 ^ 266354560;
                    n = n5;
                    n2 = n3;
                    if (n6 >= 0) break block6;
                    n = n4 = n5 + 1;
                    if (arrby[n5] >= 0) break block9;
                    n = n6 = n4 + 1;
                    n2 = n3;
                    if (arrby[n4] >= 0) break block6;
                    n = n5 = n6 + 1;
                    if (arrby[n6] >= 0) break block9;
                    n = n6 = n5 + 1;
                    n2 = n3;
                    if (arrby[n5] >= 0) break block6;
                    n = n6 + 1;
                    if (arrby[n6] >= 0) break block9;
                }
                return (int)this.readRawVarint64SlowPath();
            }
            n2 = n3;
        }
        this.bufferPos = n;
        return n2;
    }

    public long readRawVarint64() throws IOException {
        int n;
        long l;
        block5 : {
            int n2;
            block12 : {
                block3 : {
                    byte[] arrby;
                    int n3;
                    block11 : {
                        block10 : {
                            block9 : {
                                block8 : {
                                    block7 : {
                                        block6 : {
                                            block4 : {
                                                n = this.bufferSize;
                                                n3 = this.bufferPos;
                                                if (n == n3) break block3;
                                                arrby = this.buffer;
                                                n2 = n3 + 1;
                                                if ((n3 = arrby[n3]) >= 0) {
                                                    this.bufferPos = n2;
                                                    return n3;
                                                }
                                                if (n - n2 < 9) break block3;
                                                n = n2 + 1;
                                                if ((n3 = arrby[n2] << 7 ^ n3) >= 0) break block4;
                                                l = n3 ^ -128;
                                                break block5;
                                            }
                                            n2 = n + 1;
                                            if ((n3 = arrby[n] << 14 ^ n3) < 0) break block6;
                                            l = n3 ^ 16256;
                                            n = n2;
                                            break block5;
                                        }
                                        n = n2 + 1;
                                        if ((n2 = arrby[n2] << 21 ^ n3) >= 0) break block7;
                                        l = -2080896 ^ n2;
                                        break block5;
                                    }
                                    l = n2;
                                    n2 = n + 1;
                                    if ((l ^= (long)arrby[n] << 28) < 0L) break block8;
                                    l = 266354560L ^ l;
                                    n = n2;
                                    break block5;
                                }
                                n3 = n2 + 1;
                                if ((l = (long)arrby[n2] << 35 ^ l) >= 0L) break block9;
                                l = -34093383808L ^ l;
                                n = n3;
                                break block5;
                            }
                            n = n3 + 1;
                            if ((l = (long)arrby[n3] << 42 ^ l) < 0L) break block10;
                            l = 4363953127296L ^ l;
                            break block5;
                        }
                        n3 = n + 1;
                        if ((l = (long)arrby[n] << 49 ^ l) >= 0L) break block11;
                        l = -558586000294016L ^ l;
                        n = n3;
                        break block5;
                    }
                    n2 = n3 + 1;
                    if ((l = (long)arrby[n3] << 56 ^ l ^ 71499008037633920L) >= 0L) break block12;
                    n = n2 + 1;
                    if ((long)arrby[n2] >= 0L) break block5;
                }
                return this.readRawVarint64SlowPath();
            }
            n = n2;
        }
        this.bufferPos = n;
        return l;
    }

    long readRawVarint64SlowPath() throws IOException {
        long l = 0L;
        for (int i = 0; i < 64; i += 7) {
            byte by = this.readRawByte();
            l |= (long)(by & 127) << i;
            if ((by & 128) != 0) continue;
            return l;
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public int readSFixed32() throws IOException {
        return this.readRawLittleEndian32();
    }

    public long readSFixed64() throws IOException {
        return this.readRawLittleEndian64();
    }

    public int readSInt32() throws IOException {
        return CodedInputStream.decodeZigZag32(this.readRawVarint32());
    }

    public long readSInt64() throws IOException {
        return CodedInputStream.decodeZigZag64(this.readRawVarint64());
    }

    public String readString() throws IOException {
        int n;
        int n2;
        int n3 = this.readRawVarint32();
        if (n3 <= (n = this.bufferSize) - (n2 = this.bufferPos) && n3 > 0) {
            String string2 = new String(this.buffer, n2, n3, Internal.UTF_8);
            this.bufferPos += n3;
            return string2;
        }
        if (n3 == 0) {
            return "";
        }
        if (n3 <= this.bufferSize) {
            this.refillBuffer(n3);
            String string3 = new String(this.buffer, this.bufferPos, n3, Internal.UTF_8);
            this.bufferPos += n3;
            return string3;
        }
        return new String(this.readRawBytesSlowPath(n3), Internal.UTF_8);
    }

    public String readStringRequireUtf8() throws IOException {
        int n;
        byte[] arrby;
        int n2 = this.readRawVarint32();
        if (n2 <= this.bufferSize - (n = this.bufferPos) && n2 > 0) {
            arrby = this.buffer;
            this.bufferPos = n + n2;
        } else {
            if (n2 == 0) {
                return "";
            }
            if (n2 <= this.bufferSize) {
                this.refillBuffer(n2);
                arrby = this.buffer;
                n = 0;
                this.bufferPos = 0 + n2;
            } else {
                arrby = this.readRawBytesSlowPath(n2);
                n = 0;
            }
        }
        if (Utf8.isValidUtf8(arrby, n, n + n2)) {
            return new String(arrby, n, n2, Internal.UTF_8);
        }
        throw InvalidProtocolBufferException.invalidUtf8();
    }

    public int readTag() throws IOException {
        if (this.isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = this.readRawVarint32();
        if (WireFormat.getTagFieldNumber(this.lastTag) != 0) {
            return this.lastTag;
        }
        throw InvalidProtocolBufferException.invalidTag();
    }

    public int readUInt32() throws IOException {
        return this.readRawVarint32();
    }

    public long readUInt64() throws IOException {
        return this.readRawVarint64();
    }

    @Deprecated
    public void readUnknownGroup(int n, MessageLite.Builder builder) throws IOException {
        this.readGroup(n, builder, null);
    }

    public void resetSizeCounter() {
        this.totalBytesRetired = -this.bufferPos;
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
        int n2 = WireFormat.getTagWireType(n);
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 != 4) {
                            if (n2 == 5) {
                                this.skipRawBytes(4);
                                return true;
                            }
                            throw InvalidProtocolBufferException.invalidWireType();
                        }
                        return false;
                    }
                    this.skipMessage();
                    this.checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(n), 4));
                    return true;
                }
                this.skipRawBytes(this.readRawVarint32());
                return true;
            }
            this.skipRawBytes(8);
            return true;
        }
        this.skipRawVarint();
        return true;
    }

    public boolean skipField(int n, CodedOutputStream codedOutputStream) throws IOException {
        int n2 = WireFormat.getTagWireType(n);
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 != 4) {
                            if (n2 == 5) {
                                n2 = this.readRawLittleEndian32();
                                codedOutputStream.writeRawVarint32(n);
                                codedOutputStream.writeFixed32NoTag(n2);
                                return true;
                            }
                            throw InvalidProtocolBufferException.invalidWireType();
                        }
                        return false;
                    }
                    codedOutputStream.writeRawVarint32(n);
                    this.skipMessage(codedOutputStream);
                    n = WireFormat.makeTag(WireFormat.getTagFieldNumber(n), 4);
                    this.checkLastTagWas(n);
                    codedOutputStream.writeRawVarint32(n);
                    return true;
                }
                ByteString byteString = this.readBytes();
                codedOutputStream.writeRawVarint32(n);
                codedOutputStream.writeBytesNoTag(byteString);
                return true;
            }
            long l = this.readRawLittleEndian64();
            codedOutputStream.writeRawVarint32(n);
            codedOutputStream.writeFixed64NoTag(l);
            return true;
        }
        long l = this.readInt64();
        codedOutputStream.writeRawVarint32(n);
        codedOutputStream.writeUInt64NoTag(l);
        return true;
    }

    public void skipMessage() throws IOException {
        int n;
        while ((n = this.readTag()) != 0 && this.skipField(n)) {
        }
    }

    public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
        int n;
        while ((n = this.readTag()) != 0 && this.skipField(n, codedOutputStream)) {
        }
    }

    public void skipRawBytes(int n) throws IOException {
        int n2 = this.bufferSize;
        int n3 = this.bufferPos;
        if (n <= n2 - n3 && n >= 0) {
            this.bufferPos = n3 + n;
        } else {
            this.skipRawBytesSlowPath(n);
        }
    }

    private static interface RefillCallback {
        public void onRefill();
    }

    private class SkippedDataSink
    implements RefillCallback {
        private ByteArrayOutputStream byteArrayStream;
        private int lastPos;

        private SkippedDataSink() {
            this.lastPos = CodedInputStream.this.bufferPos;
        }

        ByteBuffer getSkippedData() {
            ByteArrayOutputStream byteArrayOutputStream = this.byteArrayStream;
            if (byteArrayOutputStream == null) {
                return ByteBuffer.wrap(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos - this.lastPos);
            }
            byteArrayOutputStream.write(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos);
            return ByteBuffer.wrap(this.byteArrayStream.toByteArray());
        }

        @Override
        public void onRefill() {
            if (this.byteArrayStream == null) {
                this.byteArrayStream = new ByteArrayOutputStream();
            }
            this.byteArrayStream.write(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos - this.lastPos);
            this.lastPos = 0;
        }
    }

}

