/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.protobuf.nano;

import com.android.internal.telephony.protobuf.nano.MessageNano;
import com.android.internal.telephony.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

public final class CodedOutputByteBufferNano {
    public static final int LITTLE_ENDIAN_32_SIZE = 4;
    public static final int LITTLE_ENDIAN_64_SIZE = 8;
    private static final int MAX_UTF8_EXPANSION = 3;
    private final ByteBuffer buffer;

    private CodedOutputByteBufferNano(ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    private CodedOutputByteBufferNano(byte[] arrby, int n, int n2) {
        this(ByteBuffer.wrap(arrby, n, n2));
    }

    public static int computeBoolSize(int n, boolean bl) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeBoolSizeNoTag(bl);
    }

    public static int computeBoolSizeNoTag(boolean bl) {
        return 1;
    }

    public static int computeBytesSize(int n, int n2) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeBytesSizeNoTag(n2);
    }

    public static int computeBytesSize(int n, byte[] arrby) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeBytesSizeNoTag(arrby);
    }

    public static int computeBytesSizeNoTag(int n) {
        return CodedOutputByteBufferNano.computeRawVarint32Size(n) + n;
    }

    public static int computeBytesSizeNoTag(byte[] arrby) {
        return CodedOutputByteBufferNano.computeRawVarint32Size(arrby.length) + arrby.length;
    }

    public static int computeDoubleSize(int n, double d) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeDoubleSizeNoTag(d);
    }

    public static int computeDoubleSizeNoTag(double d) {
        return 8;
    }

    public static int computeEnumSize(int n, int n2) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeEnumSizeNoTag(n2);
    }

    public static int computeEnumSizeNoTag(int n) {
        return CodedOutputByteBufferNano.computeRawVarint32Size(n);
    }

    static int computeFieldSize(int n, int n2, Object object) {
        switch (n2) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown type: ");
                ((StringBuilder)object).append(n2);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            case 18: {
                return CodedOutputByteBufferNano.computeSInt64Size(n, (Long)object);
            }
            case 17: {
                return CodedOutputByteBufferNano.computeSInt32Size(n, (Integer)object);
            }
            case 16: {
                return CodedOutputByteBufferNano.computeSFixed64Size(n, (Long)object);
            }
            case 15: {
                return CodedOutputByteBufferNano.computeSFixed32Size(n, (Integer)object);
            }
            case 14: {
                return CodedOutputByteBufferNano.computeEnumSize(n, (Integer)object);
            }
            case 13: {
                return CodedOutputByteBufferNano.computeUInt32Size(n, (Integer)object);
            }
            case 12: {
                return CodedOutputByteBufferNano.computeBytesSize(n, (byte[])object);
            }
            case 11: {
                return CodedOutputByteBufferNano.computeMessageSize(n, (MessageNano)object);
            }
            case 10: {
                return CodedOutputByteBufferNano.computeGroupSize(n, (MessageNano)object);
            }
            case 9: {
                return CodedOutputByteBufferNano.computeStringSize(n, (String)object);
            }
            case 8: {
                return CodedOutputByteBufferNano.computeBoolSize(n, (Boolean)object);
            }
            case 7: {
                return CodedOutputByteBufferNano.computeFixed32Size(n, (Integer)object);
            }
            case 6: {
                return CodedOutputByteBufferNano.computeFixed64Size(n, (Long)object);
            }
            case 5: {
                return CodedOutputByteBufferNano.computeInt32Size(n, (Integer)object);
            }
            case 4: {
                return CodedOutputByteBufferNano.computeUInt64Size(n, (Long)object);
            }
            case 3: {
                return CodedOutputByteBufferNano.computeInt64Size(n, (Long)object);
            }
            case 2: {
                return CodedOutputByteBufferNano.computeFloatSize(n, ((Float)object).floatValue());
            }
            case 1: 
        }
        return CodedOutputByteBufferNano.computeDoubleSize(n, (Double)object);
    }

    public static int computeFixed32Size(int n, int n2) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeFixed32SizeNoTag(n2);
    }

    public static int computeFixed32SizeNoTag(int n) {
        return 4;
    }

    public static int computeFixed64Size(int n, long l) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeFixed64SizeNoTag(l);
    }

    public static int computeFixed64SizeNoTag(long l) {
        return 8;
    }

    public static int computeFloatSize(int n, float f) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeFloatSizeNoTag(f);
    }

    public static int computeFloatSizeNoTag(float f) {
        return 4;
    }

    public static int computeGroupSize(int n, MessageNano messageNano) {
        return CodedOutputByteBufferNano.computeTagSize(n) * 2 + CodedOutputByteBufferNano.computeGroupSizeNoTag(messageNano);
    }

    public static int computeGroupSizeNoTag(MessageNano messageNano) {
        return messageNano.getSerializedSize();
    }

    public static int computeInt32Size(int n, int n2) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeInt32SizeNoTag(n2);
    }

    public static int computeInt32SizeNoTag(int n) {
        if (n >= 0) {
            return CodedOutputByteBufferNano.computeRawVarint32Size(n);
        }
        return 10;
    }

    public static int computeInt64Size(int n, long l) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeInt64SizeNoTag(l);
    }

    public static int computeInt64SizeNoTag(long l) {
        return CodedOutputByteBufferNano.computeRawVarint64Size(l);
    }

    public static int computeMessageSize(int n, MessageNano messageNano) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeMessageSizeNoTag(messageNano);
    }

    public static int computeMessageSizeNoTag(MessageNano messageNano) {
        int n = messageNano.getSerializedSize();
        return CodedOutputByteBufferNano.computeRawVarint32Size(n) + n;
    }

    public static int computeRawVarint32Size(int n) {
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

    public static int computeRawVarint64Size(long l) {
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

    public static int computeSFixed32Size(int n, int n2) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeSFixed32SizeNoTag(n2);
    }

    public static int computeSFixed32SizeNoTag(int n) {
        return 4;
    }

    public static int computeSFixed64Size(int n, long l) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeSFixed64SizeNoTag(l);
    }

    public static int computeSFixed64SizeNoTag(long l) {
        return 8;
    }

    public static int computeSInt32Size(int n, int n2) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeSInt32SizeNoTag(n2);
    }

    public static int computeSInt32SizeNoTag(int n) {
        return CodedOutputByteBufferNano.computeRawVarint32Size(CodedOutputByteBufferNano.encodeZigZag32(n));
    }

    public static int computeSInt64Size(int n, long l) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeSInt64SizeNoTag(l);
    }

    public static int computeSInt64SizeNoTag(long l) {
        return CodedOutputByteBufferNano.computeRawVarint64Size(CodedOutputByteBufferNano.encodeZigZag64(l));
    }

    public static int computeStringSize(int n, String string) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeStringSizeNoTag(string);
    }

    public static int computeStringSizeNoTag(String string) {
        int n = CodedOutputByteBufferNano.encodedLength(string);
        return CodedOutputByteBufferNano.computeRawVarint32Size(n) + n;
    }

    public static int computeTagSize(int n) {
        return CodedOutputByteBufferNano.computeRawVarint32Size(WireFormatNano.makeTag(n, 0));
    }

    public static int computeUInt32Size(int n, int n2) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeUInt32SizeNoTag(n2);
    }

    public static int computeUInt32SizeNoTag(int n) {
        return CodedOutputByteBufferNano.computeRawVarint32Size(n);
    }

    public static int computeUInt64Size(int n, long l) {
        return CodedOutputByteBufferNano.computeTagSize(n) + CodedOutputByteBufferNano.computeUInt64SizeNoTag(l);
    }

    public static int computeUInt64SizeNoTag(long l) {
        return CodedOutputByteBufferNano.computeRawVarint64Size(l);
    }

    private static int encode(CharSequence charSequence, byte[] arrby, int n, int n2) {
        int n3 = charSequence.length();
        int n4 = 0;
        int n5 = n + n2;
        n2 = n4;
        while (n2 < n3 && n2 + n < n5 && (n4 = (int)charSequence.charAt(n2)) < 128) {
            arrby[n + n2] = (byte)n4;
            ++n2;
        }
        if (n2 == n3) {
            return n + n3;
        }
        n += n2;
        while (n2 < n3) {
            char c;
            block10 : {
                block11 : {
                    block7 : {
                        block9 : {
                            block8 : {
                                block6 : {
                                    c = charSequence.charAt(n2);
                                    if (c >= '' || n >= n5) break block6;
                                    arrby[n] = (byte)c;
                                    ++n;
                                    break block7;
                                }
                                if (c >= '\u0800' || n > n5 - 2) break block8;
                                n4 = n + 1;
                                arrby[n] = (byte)(c >>> 6 | 960);
                                n = n4 + 1;
                                arrby[n4] = (byte)(c & 63 | 128);
                                break block7;
                            }
                            if (c >= '\ud800' && '\udfff' >= c || n > n5 - 3) break block9;
                            n4 = n + 1;
                            arrby[n] = (byte)(c >>> 12 | 480);
                            n = n4 + 1;
                            arrby[n4] = (byte)(c >>> 6 & 63 | 128);
                            arrby[n] = (byte)(c & 63 | 128);
                            ++n;
                            break block7;
                        }
                        if (n > n5 - 4) break block10;
                        n4 = n2;
                        if (n2 + 1 == charSequence.length()) break block11;
                        char c2 = charSequence.charAt(++n2);
                        n4 = n2;
                        if (!Character.isSurrogatePair(c, c2)) break block11;
                        n4 = Character.toCodePoint(c, c2);
                        int n6 = n + 1;
                        arrby[n] = (byte)(n4 >>> 18 | 240);
                        n = n6 + 1;
                        arrby[n6] = (byte)(n4 >>> 12 & 63 | 128);
                        n6 = n + 1;
                        arrby[n] = (byte)(n4 >>> 6 & 63 | 128);
                        n = n6 + 1;
                        arrby[n6] = (byte)(n4 & 63 | 128);
                    }
                    ++n2;
                    continue;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unpaired surrogate at index ");
                ((StringBuilder)charSequence).append(n4 - 1);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Failed writing ");
            ((StringBuilder)charSequence).append(c);
            ((StringBuilder)charSequence).append(" at index ");
            ((StringBuilder)charSequence).append(n);
            throw new ArrayIndexOutOfBoundsException(((StringBuilder)charSequence).toString());
        }
        return n;
    }

    private static void encode(CharSequence object, ByteBuffer byteBuffer) {
        if (!byteBuffer.isReadOnly()) {
            if (byteBuffer.hasArray()) {
                try {
                    byteBuffer.position(CodedOutputByteBufferNano.encode((CharSequence)object, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
                }
                catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    object = new BufferOverflowException();
                    ((Throwable)object).initCause(arrayIndexOutOfBoundsException);
                    throw object;
                }
            } else {
                CodedOutputByteBufferNano.encodeDirect((CharSequence)object, byteBuffer);
            }
            return;
        }
        throw new ReadOnlyBufferException();
    }

    private static void encodeDirect(CharSequence charSequence, ByteBuffer byteBuffer) {
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            char c = charSequence.charAt(i);
            if (c < '') {
                byteBuffer.put((byte)c);
                continue;
            }
            if (c < '\u0800') {
                byteBuffer.put((byte)(c >>> 6 | 960));
                byteBuffer.put((byte)(128 | c & 63));
                continue;
            }
            if (c >= '\ud800' && '\udfff' >= c) {
                int n2 = i;
                if (i + 1 != charSequence.length()) {
                    char c2 = charSequence.charAt(++i);
                    n2 = i;
                    if (Character.isSurrogatePair(c, c2)) {
                        n2 = Character.toCodePoint(c, c2);
                        byteBuffer.put((byte)(n2 >>> 18 | 240));
                        byteBuffer.put((byte)(n2 >>> 12 & 63 | 128));
                        byteBuffer.put((byte)(n2 >>> 6 & 63 | 128));
                        byteBuffer.put((byte)(128 | n2 & 63));
                        continue;
                    }
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unpaired surrogate at index ");
                ((StringBuilder)charSequence).append(n2 - 1);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            byteBuffer.put((byte)(c >>> 12 | 480));
            byteBuffer.put((byte)(c >>> 6 & 63 | 128));
            byteBuffer.put((byte)(128 | c & 63));
        }
    }

    public static int encodeZigZag32(int n) {
        return n << 1 ^ n >> 31;
    }

    public static long encodeZigZag64(long l) {
        return l << 1 ^ l >> 63;
    }

    private static int encodedLength(CharSequence charSequence) {
        int n;
        int n2;
        block3 : {
            int n3;
            int n4;
            int n5 = n = charSequence.length();
            n2 = 0;
            do {
                n4 = n5;
                n3 = n2;
                if (n2 >= n) break;
                n4 = n5;
                n3 = n2;
                if (charSequence.charAt(n2) >= '') break;
                ++n2;
            } while (true);
            do {
                n2 = n4;
                if (n3 >= n) break block3;
                n2 = charSequence.charAt(n3);
                if (n2 >= 2048) break;
                n4 += 127 - n2 >>> 31;
                ++n3;
            } while (true);
            n2 = n4 + CodedOutputByteBufferNano.encodedLengthGeneral(charSequence, n3);
        }
        if (n2 >= n) {
            return n2;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("UTF-8 length does not fit in int: ");
        ((StringBuilder)charSequence).append((long)n2 + 0x100000000L);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static int encodedLengthGeneral(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        int n3 = 0;
        while (n < n2) {
            int n4;
            char c = charSequence.charAt(n);
            if (c < '\u0800') {
                n3 += 127 - c >>> 31;
                n4 = n;
            } else {
                int n5;
                n3 = n5 = n3 + 2;
                n4 = n;
                if ('\ud800' <= c) {
                    n3 = n5;
                    n4 = n;
                    if (c <= '\udfff') {
                        if (Character.codePointAt(charSequence, n) >= 65536) {
                            n4 = n + 1;
                            n3 = n5;
                        } else {
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("Unpaired surrogate at index ");
                            ((StringBuilder)charSequence).append(n);
                            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                        }
                    }
                }
            }
            n = n4 + 1;
        }
        return n3;
    }

    public static CodedOutputByteBufferNano newInstance(byte[] arrby) {
        return CodedOutputByteBufferNano.newInstance(arrby, 0, arrby.length);
    }

    public static CodedOutputByteBufferNano newInstance(byte[] arrby, int n, int n2) {
        return new CodedOutputByteBufferNano(arrby, n, n2);
    }

    public void checkNoSpaceLeft() {
        if (this.spaceLeft() == 0) {
            return;
        }
        throw new IllegalStateException("Did not write as much data as expected.");
    }

    public int position() {
        return this.buffer.position();
    }

    public void reset() {
        this.buffer.clear();
    }

    public int spaceLeft() {
        return this.buffer.remaining();
    }

    public void writeBool(int n, boolean bl) throws IOException {
        this.writeTag(n, 0);
        this.writeBoolNoTag(bl);
    }

    public void writeBoolNoTag(boolean bl) throws IOException {
        this.writeRawByte((int)bl);
    }

    public void writeBytes(int n, byte[] arrby) throws IOException {
        this.writeTag(n, 2);
        this.writeBytesNoTag(arrby);
    }

    public void writeBytes(int n, byte[] arrby, int n2, int n3) throws IOException {
        this.writeTag(n, 2);
        this.writeBytesNoTag(arrby, n2, n3);
    }

    public void writeBytesNoTag(byte[] arrby) throws IOException {
        this.writeRawVarint32(arrby.length);
        this.writeRawBytes(arrby);
    }

    public void writeBytesNoTag(byte[] arrby, int n, int n2) throws IOException {
        this.writeRawVarint32(n2);
        this.writeRawBytes(arrby, n, n2);
    }

    public void writeDouble(int n, double d) throws IOException {
        this.writeTag(n, 1);
        this.writeDoubleNoTag(d);
    }

    public void writeDoubleNoTag(double d) throws IOException {
        this.writeRawLittleEndian64(Double.doubleToLongBits(d));
    }

    public void writeEnum(int n, int n2) throws IOException {
        this.writeTag(n, 0);
        this.writeEnumNoTag(n2);
    }

    public void writeEnumNoTag(int n) throws IOException {
        this.writeRawVarint32(n);
    }

    void writeField(int n, int n2, Object object) throws IOException {
        switch (n2) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown type: ");
                ((StringBuilder)object).append(n2);
                throw new IOException(((StringBuilder)object).toString());
            }
            case 18: {
                this.writeSInt64(n, (Long)object);
                break;
            }
            case 17: {
                this.writeSInt32(n, (Integer)object);
                break;
            }
            case 16: {
                this.writeSFixed64(n, (Long)object);
                break;
            }
            case 15: {
                this.writeSFixed32(n, (Integer)object);
                break;
            }
            case 14: {
                this.writeEnum(n, (Integer)object);
                break;
            }
            case 13: {
                this.writeUInt32(n, (Integer)object);
                break;
            }
            case 12: {
                this.writeBytes(n, (byte[])object);
                break;
            }
            case 11: {
                this.writeMessage(n, (MessageNano)object);
                break;
            }
            case 10: {
                this.writeGroup(n, (MessageNano)object);
                break;
            }
            case 9: {
                this.writeString(n, (String)object);
                break;
            }
            case 8: {
                this.writeBool(n, (Boolean)object);
                break;
            }
            case 7: {
                this.writeFixed32(n, (Integer)object);
                break;
            }
            case 6: {
                this.writeFixed64(n, (Long)object);
                break;
            }
            case 5: {
                this.writeInt32(n, (Integer)object);
                break;
            }
            case 4: {
                this.writeUInt64(n, (Long)object);
                break;
            }
            case 3: {
                this.writeInt64(n, (Long)object);
                break;
            }
            case 2: {
                this.writeFloat(n, ((Float)object).floatValue());
                break;
            }
            case 1: {
                this.writeDouble(n, (Double)object);
            }
        }
    }

    public void writeFixed32(int n, int n2) throws IOException {
        this.writeTag(n, 5);
        this.writeFixed32NoTag(n2);
    }

    public void writeFixed32NoTag(int n) throws IOException {
        this.writeRawLittleEndian32(n);
    }

    public void writeFixed64(int n, long l) throws IOException {
        this.writeTag(n, 1);
        this.writeFixed64NoTag(l);
    }

    public void writeFixed64NoTag(long l) throws IOException {
        this.writeRawLittleEndian64(l);
    }

    public void writeFloat(int n, float f) throws IOException {
        this.writeTag(n, 5);
        this.writeFloatNoTag(f);
    }

    public void writeFloatNoTag(float f) throws IOException {
        this.writeRawLittleEndian32(Float.floatToIntBits(f));
    }

    public void writeGroup(int n, MessageNano messageNano) throws IOException {
        this.writeTag(n, 3);
        this.writeGroupNoTag(messageNano);
        this.writeTag(n, 4);
    }

    public void writeGroupNoTag(MessageNano messageNano) throws IOException {
        messageNano.writeTo(this);
    }

    public void writeInt32(int n, int n2) throws IOException {
        this.writeTag(n, 0);
        this.writeInt32NoTag(n2);
    }

    public void writeInt32NoTag(int n) throws IOException {
        if (n >= 0) {
            this.writeRawVarint32(n);
        } else {
            this.writeRawVarint64(n);
        }
    }

    public void writeInt64(int n, long l) throws IOException {
        this.writeTag(n, 0);
        this.writeInt64NoTag(l);
    }

    public void writeInt64NoTag(long l) throws IOException {
        this.writeRawVarint64(l);
    }

    public void writeMessage(int n, MessageNano messageNano) throws IOException {
        this.writeTag(n, 2);
        this.writeMessageNoTag(messageNano);
    }

    public void writeMessageNoTag(MessageNano messageNano) throws IOException {
        this.writeRawVarint32(messageNano.getCachedSize());
        messageNano.writeTo(this);
    }

    public void writeRawByte(byte by) throws IOException {
        if (this.buffer.hasRemaining()) {
            this.buffer.put(by);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public void writeRawByte(int n) throws IOException {
        this.writeRawByte((byte)n);
    }

    public void writeRawBytes(byte[] arrby) throws IOException {
        this.writeRawBytes(arrby, 0, arrby.length);
    }

    public void writeRawBytes(byte[] arrby, int n, int n2) throws IOException {
        if (this.buffer.remaining() >= n2) {
            this.buffer.put(arrby, n, n2);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public void writeRawLittleEndian32(int n) throws IOException {
        if (this.buffer.remaining() >= 4) {
            this.buffer.putInt(n);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public void writeRawLittleEndian64(long l) throws IOException {
        if (this.buffer.remaining() >= 8) {
            this.buffer.putLong(l);
            return;
        }
        throw new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public void writeRawVarint32(int n) throws IOException {
        do {
            if ((n & -128) == 0) {
                this.writeRawByte(n);
                return;
            }
            this.writeRawByte(n & 127 | 128);
            n >>>= 7;
        } while (true);
    }

    public void writeRawVarint64(long l) throws IOException {
        do {
            if ((-128L & l) == 0L) {
                this.writeRawByte((int)l);
                return;
            }
            this.writeRawByte((int)l & 127 | 128);
            l >>>= 7;
        } while (true);
    }

    public void writeSFixed32(int n, int n2) throws IOException {
        this.writeTag(n, 5);
        this.writeSFixed32NoTag(n2);
    }

    public void writeSFixed32NoTag(int n) throws IOException {
        this.writeRawLittleEndian32(n);
    }

    public void writeSFixed64(int n, long l) throws IOException {
        this.writeTag(n, 1);
        this.writeSFixed64NoTag(l);
    }

    public void writeSFixed64NoTag(long l) throws IOException {
        this.writeRawLittleEndian64(l);
    }

    public void writeSInt32(int n, int n2) throws IOException {
        this.writeTag(n, 0);
        this.writeSInt32NoTag(n2);
    }

    public void writeSInt32NoTag(int n) throws IOException {
        this.writeRawVarint32(CodedOutputByteBufferNano.encodeZigZag32(n));
    }

    public void writeSInt64(int n, long l) throws IOException {
        this.writeTag(n, 0);
        this.writeSInt64NoTag(l);
    }

    public void writeSInt64NoTag(long l) throws IOException {
        this.writeRawVarint64(CodedOutputByteBufferNano.encodeZigZag64(l));
    }

    public void writeString(int n, String string) throws IOException {
        this.writeTag(n, 2);
        this.writeStringNoTag(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeStringNoTag(String object) throws IOException {
        try {
            int n = CodedOutputByteBufferNano.computeRawVarint32Size(((String)object).length());
            if (n != CodedOutputByteBufferNano.computeRawVarint32Size(((String)object).length() * 3)) {
                this.writeRawVarint32(CodedOutputByteBufferNano.encodedLength((CharSequence)object));
                CodedOutputByteBufferNano.encode((CharSequence)object, this.buffer);
                return;
            }
            int n2 = this.buffer.position();
            if (this.buffer.remaining() >= n) {
                this.buffer.position(n2 + n);
                CodedOutputByteBufferNano.encode((CharSequence)object, this.buffer);
                int n3 = this.buffer.position();
                this.buffer.position(n2);
                this.writeRawVarint32(n3 - n2 - n);
                this.buffer.position(n3);
                return;
            }
            object = new OutOfSpaceException(n2 + n, this.buffer.limit());
            throw object;
        }
        catch (BufferOverflowException bufferOverflowException) {
            OutOfSpaceException outOfSpaceException = new OutOfSpaceException(this.buffer.position(), this.buffer.limit());
            outOfSpaceException.initCause(bufferOverflowException);
            throw outOfSpaceException;
        }
    }

    public void writeTag(int n, int n2) throws IOException {
        this.writeRawVarint32(WireFormatNano.makeTag(n, n2));
    }

    public void writeUInt32(int n, int n2) throws IOException {
        this.writeTag(n, 0);
        this.writeUInt32NoTag(n2);
    }

    public void writeUInt32NoTag(int n) throws IOException {
        this.writeRawVarint32(n);
    }

    public void writeUInt64(int n, long l) throws IOException {
        this.writeTag(n, 0);
        this.writeUInt64NoTag(l);
    }

    public void writeUInt64NoTag(long l) throws IOException {
        this.writeRawVarint64(l);
    }

    public static class OutOfSpaceException
    extends IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        OutOfSpaceException(int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CodedOutputStream was writing to a flat byte array and ran out of space (pos ");
            stringBuilder.append(n);
            stringBuilder.append(" limit ");
            stringBuilder.append(n2);
            stringBuilder.append(").");
            super(stringBuilder.toString());
        }
    }

}

