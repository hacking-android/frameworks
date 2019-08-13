/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteOutput;
import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.Internal;
import com.android.framework.protobuf.LazyFieldLite;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.Utf8;
import com.android.framework.protobuf.WireFormat;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

public abstract class CodedOutputStream
extends ByteOutput {
    private static final long ARRAY_BASE_OFFSET;
    public static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int FIXED_32_SIZE = 4;
    private static final int FIXED_64_SIZE = 8;
    private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS;
    @Deprecated
    public static final int LITTLE_ENDIAN_32_SIZE = 4;
    private static final int MAX_VARINT_SIZE = 10;
    private static final Unsafe UNSAFE;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(CodedOutputStream.class.getName());
        UNSAFE = CodedOutputStream.getUnsafe();
        HAS_UNSAFE_ARRAY_OPERATIONS = CodedOutputStream.supportsUnsafeArrayOperations();
        ARRAY_BASE_OFFSET = CodedOutputStream.byteArrayBaseOffset();
    }

    private CodedOutputStream() {
    }

    private static <T> int byteArrayBaseOffset() {
        int n = HAS_UNSAFE_ARRAY_OPERATIONS ? UNSAFE.arrayBaseOffset(byte[].class) : -1;
        return n;
    }

    public static int computeBoolSize(int n, boolean bl) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeBoolSizeNoTag(bl);
    }

    public static int computeBoolSizeNoTag(boolean bl) {
        return 1;
    }

    public static int computeByteArraySize(int n, byte[] arrby) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeByteArraySizeNoTag(arrby);
    }

    public static int computeByteArraySizeNoTag(byte[] arrby) {
        return CodedOutputStream.computeLengthDelimitedFieldSize(arrby.length);
    }

    public static int computeByteBufferSize(int n, ByteBuffer byteBuffer) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeByteBufferSizeNoTag(byteBuffer);
    }

    public static int computeByteBufferSizeNoTag(ByteBuffer byteBuffer) {
        return CodedOutputStream.computeLengthDelimitedFieldSize(byteBuffer.capacity());
    }

    public static int computeBytesSize(int n, ByteString byteString) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeBytesSizeNoTag(byteString);
    }

    public static int computeBytesSizeNoTag(ByteString byteString) {
        return CodedOutputStream.computeLengthDelimitedFieldSize(byteString.size());
    }

    public static int computeDoubleSize(int n, double d) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeDoubleSizeNoTag(d);
    }

    public static int computeDoubleSizeNoTag(double d) {
        return 8;
    }

    public static int computeEnumSize(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeEnumSizeNoTag(n2);
    }

    public static int computeEnumSizeNoTag(int n) {
        return CodedOutputStream.computeInt32SizeNoTag(n);
    }

    public static int computeFixed32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeFixed32SizeNoTag(n2);
    }

    public static int computeFixed32SizeNoTag(int n) {
        return 4;
    }

    public static int computeFixed64Size(int n, long l) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeFixed64SizeNoTag(l);
    }

    public static int computeFixed64SizeNoTag(long l) {
        return 8;
    }

    public static int computeFloatSize(int n, float f) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeFloatSizeNoTag(f);
    }

    public static int computeFloatSizeNoTag(float f) {
        return 4;
    }

    @Deprecated
    public static int computeGroupSize(int n, MessageLite messageLite) {
        return CodedOutputStream.computeTagSize(n) * 2 + CodedOutputStream.computeGroupSizeNoTag(messageLite);
    }

    @Deprecated
    public static int computeGroupSizeNoTag(MessageLite messageLite) {
        return messageLite.getSerializedSize();
    }

    public static int computeInt32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeInt32SizeNoTag(n2);
    }

    public static int computeInt32SizeNoTag(int n) {
        if (n >= 0) {
            return CodedOutputStream.computeUInt32SizeNoTag(n);
        }
        return 10;
    }

    public static int computeInt64Size(int n, long l) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeInt64SizeNoTag(l);
    }

    public static int computeInt64SizeNoTag(long l) {
        return CodedOutputStream.computeUInt64SizeNoTag(l);
    }

    public static int computeLazyFieldMessageSetExtensionSize(int n, LazyFieldLite lazyFieldLite) {
        return CodedOutputStream.computeTagSize(1) * 2 + CodedOutputStream.computeUInt32Size(2, n) + CodedOutputStream.computeLazyFieldSize(3, lazyFieldLite);
    }

    public static int computeLazyFieldSize(int n, LazyFieldLite lazyFieldLite) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeLazyFieldSizeNoTag(lazyFieldLite);
    }

    public static int computeLazyFieldSizeNoTag(LazyFieldLite lazyFieldLite) {
        return CodedOutputStream.computeLengthDelimitedFieldSize(lazyFieldLite.getSerializedSize());
    }

    private static int computeLengthDelimitedFieldSize(int n) {
        return CodedOutputStream.computeUInt32SizeNoTag(n) + n;
    }

    public static int computeMessageSetExtensionSize(int n, MessageLite messageLite) {
        return CodedOutputStream.computeTagSize(1) * 2 + CodedOutputStream.computeUInt32Size(2, n) + CodedOutputStream.computeMessageSize(3, messageLite);
    }

    public static int computeMessageSize(int n, MessageLite messageLite) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeMessageSizeNoTag(messageLite);
    }

    public static int computeMessageSizeNoTag(MessageLite messageLite) {
        return CodedOutputStream.computeLengthDelimitedFieldSize(messageLite.getSerializedSize());
    }

    static int computePreferredBufferSize(int n) {
        if (n > 4096) {
            return 4096;
        }
        return n;
    }

    public static int computeRawMessageSetExtensionSize(int n, ByteString byteString) {
        return CodedOutputStream.computeTagSize(1) * 2 + CodedOutputStream.computeUInt32Size(2, n) + CodedOutputStream.computeBytesSize(3, byteString);
    }

    @Deprecated
    public static int computeRawVarint32Size(int n) {
        return CodedOutputStream.computeUInt32SizeNoTag(n);
    }

    @Deprecated
    public static int computeRawVarint64Size(long l) {
        return CodedOutputStream.computeUInt64SizeNoTag(l);
    }

    public static int computeSFixed32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeSFixed32SizeNoTag(n2);
    }

    public static int computeSFixed32SizeNoTag(int n) {
        return 4;
    }

    public static int computeSFixed64Size(int n, long l) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeSFixed64SizeNoTag(l);
    }

    public static int computeSFixed64SizeNoTag(long l) {
        return 8;
    }

    public static int computeSInt32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeSInt32SizeNoTag(n2);
    }

    public static int computeSInt32SizeNoTag(int n) {
        return CodedOutputStream.computeUInt32SizeNoTag(CodedOutputStream.encodeZigZag32(n));
    }

    public static int computeSInt64Size(int n, long l) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeSInt64SizeNoTag(l);
    }

    public static int computeSInt64SizeNoTag(long l) {
        return CodedOutputStream.computeUInt64SizeNoTag(CodedOutputStream.encodeZigZag64(l));
    }

    public static int computeStringSize(int n, String string2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeStringSizeNoTag(string2);
    }

    public static int computeStringSizeNoTag(String string2) {
        int n;
        try {
            n = Utf8.encodedLength(string2);
        }
        catch (Utf8.UnpairedSurrogateException unpairedSurrogateException) {
            n = string2.getBytes(Internal.UTF_8).length;
        }
        return CodedOutputStream.computeLengthDelimitedFieldSize(n);
    }

    public static int computeTagSize(int n) {
        return CodedOutputStream.computeUInt32SizeNoTag(WireFormat.makeTag(n, 0));
    }

    public static int computeUInt32Size(int n, int n2) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeUInt32SizeNoTag(n2);
    }

    public static int computeUInt32SizeNoTag(int n) {
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

    public static int computeUInt64Size(int n, long l) {
        return CodedOutputStream.computeTagSize(n) + CodedOutputStream.computeUInt64SizeNoTag(l);
    }

    public static int computeUInt64SizeNoTag(long l) {
        if ((-128L & l) == 0L) {
            return 1;
        }
        if (l < 0L) {
            return 10;
        }
        int n = 2;
        long l2 = l;
        if ((-34359738368L & l) != 0L) {
            n = 2 + 4;
            l2 = l >>> 28;
        }
        int n2 = n;
        l = l2;
        if ((-2097152L & l2) != 0L) {
            n2 = n + 2;
            l = l2 >>> 14;
        }
        n = n2;
        if ((-16384L & l) != 0L) {
            n = n2 + 1;
        }
        return n;
    }

    public static int encodeZigZag32(int n) {
        return n << 1 ^ n >> 31;
    }

    public static long encodeZigZag64(long l) {
        return l << 1 ^ l >> 63;
    }

    private static Unsafe getUnsafe() {
        Object object;
        PrivilegedExceptionAction<Unsafe> privilegedExceptionAction = null;
        try {
            object = new PrivilegedExceptionAction<Unsafe>(){

                @Override
                public Unsafe run() throws Exception {
                    for (Field field : Unsafe.class.getDeclaredFields()) {
                        field.setAccessible(true);
                        Object object = field.get(null);
                        if (!Unsafe.class.isInstance(object)) continue;
                        return (Unsafe)Unsafe.class.cast(object);
                    }
                    return null;
                }
            };
            privilegedExceptionAction = object = AccessController.doPrivileged(object);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        Logger logger = CodedOutputStream.logger;
        Level level = Level.FINEST;
        object = privilegedExceptionAction != null ? "available" : "unavailable";
        logger.log(level, "sun.misc.Unsafe: {}", object);
        return privilegedExceptionAction;
    }

    static CodedOutputStream newInstance(ByteOutput byteOutput, int n) {
        if (n >= 0) {
            return new ByteOutputEncoder(byteOutput, n);
        }
        throw new IllegalArgumentException("bufferSize must be positive");
    }

    public static CodedOutputStream newInstance(OutputStream outputStream) {
        return CodedOutputStream.newInstance(outputStream, 4096);
    }

    public static CodedOutputStream newInstance(OutputStream outputStream, int n) {
        return new OutputStreamEncoder(outputStream, n);
    }

    public static CodedOutputStream newInstance(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return new NioHeapEncoder(byteBuffer);
        }
        return new NioEncoder(byteBuffer);
    }

    @Deprecated
    public static CodedOutputStream newInstance(ByteBuffer byteBuffer, int n) {
        return CodedOutputStream.newInstance(byteBuffer);
    }

    public static CodedOutputStream newInstance(byte[] arrby) {
        return CodedOutputStream.newInstance(arrby, 0, arrby.length);
    }

    public static CodedOutputStream newInstance(byte[] arrby, int n, int n2) {
        return new ArrayEncoder(arrby, n, n2);
    }

    private static boolean supportsUnsafeArrayOperations() {
        boolean bl = false;
        Object object = UNSAFE;
        boolean bl2 = bl;
        if (object != null) {
            try {
                object.getClass().getMethod("arrayBaseOffset", Class.class);
                UNSAFE.getClass().getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
                bl2 = true;
            }
            catch (Throwable throwable) {
                bl2 = bl;
            }
        }
        Logger logger = CodedOutputStream.logger;
        Level level = Level.FINEST;
        object = bl2 ? "available" : "unavailable";
        logger.log(level, "Unsafe array operations: {}", object);
        return bl2;
    }

    public final void checkNoSpaceLeft() {
        if (this.spaceLeft() == 0) {
            return;
        }
        throw new IllegalStateException("Did not write as much data as expected.");
    }

    public abstract void flush() throws IOException;

    public abstract int getTotalBytesWritten();

    final void inefficientWriteStringNoTag(String arrby, Utf8.UnpairedSurrogateException unpairedSurrogateException) throws IOException {
        logger.log(Level.WARNING, "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", unpairedSurrogateException);
        arrby = arrby.getBytes(Internal.UTF_8);
        try {
            this.writeUInt32NoTag(arrby.length);
            this.writeLazy(arrby, 0, arrby.length);
            return;
        }
        catch (OutOfSpaceException outOfSpaceException) {
            throw outOfSpaceException;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new OutOfSpaceException(indexOutOfBoundsException);
        }
    }

    public abstract int spaceLeft();

    @Override
    public abstract void write(byte var1) throws IOException;

    @Override
    public abstract void write(ByteBuffer var1) throws IOException;

    @Override
    public abstract void write(byte[] var1, int var2, int var3) throws IOException;

    public abstract void writeBool(int var1, boolean var2) throws IOException;

    public final void writeBoolNoTag(boolean bl) throws IOException {
        this.write((byte)(bl ? 1 : 0));
    }

    public abstract void writeByteArray(int var1, byte[] var2) throws IOException;

    public abstract void writeByteArray(int var1, byte[] var2, int var3, int var4) throws IOException;

    public final void writeByteArrayNoTag(byte[] arrby) throws IOException {
        this.writeByteArrayNoTag(arrby, 0, arrby.length);
    }

    abstract void writeByteArrayNoTag(byte[] var1, int var2, int var3) throws IOException;

    public abstract void writeByteBuffer(int var1, ByteBuffer var2) throws IOException;

    public abstract void writeBytes(int var1, ByteString var2) throws IOException;

    public abstract void writeBytesNoTag(ByteString var1) throws IOException;

    public final void writeDouble(int n, double d) throws IOException {
        this.writeFixed64(n, Double.doubleToRawLongBits(d));
    }

    public final void writeDoubleNoTag(double d) throws IOException {
        this.writeFixed64NoTag(Double.doubleToRawLongBits(d));
    }

    public final void writeEnum(int n, int n2) throws IOException {
        this.writeInt32(n, n2);
    }

    public final void writeEnumNoTag(int n) throws IOException {
        this.writeInt32NoTag(n);
    }

    public abstract void writeFixed32(int var1, int var2) throws IOException;

    public abstract void writeFixed32NoTag(int var1) throws IOException;

    public abstract void writeFixed64(int var1, long var2) throws IOException;

    public abstract void writeFixed64NoTag(long var1) throws IOException;

    public final void writeFloat(int n, float f) throws IOException {
        this.writeFixed32(n, Float.floatToRawIntBits(f));
    }

    public final void writeFloatNoTag(float f) throws IOException {
        this.writeFixed32NoTag(Float.floatToRawIntBits(f));
    }

    @Deprecated
    public final void writeGroup(int n, MessageLite messageLite) throws IOException {
        this.writeTag(n, 3);
        this.writeGroupNoTag(messageLite);
        this.writeTag(n, 4);
    }

    @Deprecated
    public final void writeGroupNoTag(MessageLite messageLite) throws IOException {
        messageLite.writeTo(this);
    }

    public abstract void writeInt32(int var1, int var2) throws IOException;

    public abstract void writeInt32NoTag(int var1) throws IOException;

    public final void writeInt64(int n, long l) throws IOException {
        this.writeUInt64(n, l);
    }

    public final void writeInt64NoTag(long l) throws IOException {
        this.writeUInt64NoTag(l);
    }

    @Override
    public abstract void writeLazy(ByteBuffer var1) throws IOException;

    @Override
    public abstract void writeLazy(byte[] var1, int var2, int var3) throws IOException;

    public abstract void writeMessage(int var1, MessageLite var2) throws IOException;

    public abstract void writeMessageNoTag(MessageLite var1) throws IOException;

    public abstract void writeMessageSetExtension(int var1, MessageLite var2) throws IOException;

    public final void writeRawByte(byte by) throws IOException {
        this.write(by);
    }

    public final void writeRawByte(int n) throws IOException {
        this.write((byte)n);
    }

    public final void writeRawBytes(ByteString byteString) throws IOException {
        byteString.writeTo(this);
    }

    public abstract void writeRawBytes(ByteBuffer var1) throws IOException;

    public final void writeRawBytes(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    public final void writeRawBytes(byte[] arrby, int n, int n2) throws IOException {
        this.write(arrby, n, n2);
    }

    @Deprecated
    public final void writeRawLittleEndian32(int n) throws IOException {
        this.writeFixed32NoTag(n);
    }

    @Deprecated
    public final void writeRawLittleEndian64(long l) throws IOException {
        this.writeFixed64NoTag(l);
    }

    public abstract void writeRawMessageSetExtension(int var1, ByteString var2) throws IOException;

    @Deprecated
    public final void writeRawVarint32(int n) throws IOException {
        this.writeUInt32NoTag(n);
    }

    @Deprecated
    public final void writeRawVarint64(long l) throws IOException {
        this.writeUInt64NoTag(l);
    }

    public final void writeSFixed32(int n, int n2) throws IOException {
        this.writeFixed32(n, n2);
    }

    public final void writeSFixed32NoTag(int n) throws IOException {
        this.writeFixed32NoTag(n);
    }

    public final void writeSFixed64(int n, long l) throws IOException {
        this.writeFixed64(n, l);
    }

    public final void writeSFixed64NoTag(long l) throws IOException {
        this.writeFixed64NoTag(l);
    }

    public final void writeSInt32(int n, int n2) throws IOException {
        this.writeUInt32(n, CodedOutputStream.encodeZigZag32(n2));
    }

    public final void writeSInt32NoTag(int n) throws IOException {
        this.writeUInt32NoTag(CodedOutputStream.encodeZigZag32(n));
    }

    public final void writeSInt64(int n, long l) throws IOException {
        this.writeUInt64(n, CodedOutputStream.encodeZigZag64(l));
    }

    public final void writeSInt64NoTag(long l) throws IOException {
        this.writeUInt64NoTag(CodedOutputStream.encodeZigZag64(l));
    }

    public abstract void writeString(int var1, String var2) throws IOException;

    public abstract void writeStringNoTag(String var1) throws IOException;

    public abstract void writeTag(int var1, int var2) throws IOException;

    public abstract void writeUInt32(int var1, int var2) throws IOException;

    public abstract void writeUInt32NoTag(int var1) throws IOException;

    public abstract void writeUInt64(int var1, long var2) throws IOException;

    public abstract void writeUInt64NoTag(long var1) throws IOException;

    private static abstract class AbstractBufferedEncoder
    extends CodedOutputStream {
        final byte[] buffer;
        final int limit;
        int position;
        int totalBytesWritten;

        AbstractBufferedEncoder(int n) {
            if (n >= 0) {
                this.buffer = new byte[Math.max(n, 20)];
                this.limit = this.buffer.length;
                return;
            }
            throw new IllegalArgumentException("bufferSize must be >= 0");
        }

        final void buffer(byte by) {
            byte[] arrby = this.buffer;
            int n = this.position;
            this.position = n + 1;
            arrby[n] = by;
            ++this.totalBytesWritten;
        }

        final void bufferFixed32NoTag(int n) {
            byte[] arrby = this.buffer;
            int n2 = this.position;
            this.position = n2 + 1;
            arrby[n2] = (byte)(n & 255);
            n2 = this.position;
            this.position = n2 + 1;
            arrby[n2] = (byte)(n >> 8 & 255);
            n2 = this.position;
            this.position = n2 + 1;
            arrby[n2] = (byte)(n >> 16 & 255);
            n2 = this.position;
            this.position = n2 + 1;
            arrby[n2] = (byte)(n >> 24 & 255);
            this.totalBytesWritten += 4;
        }

        final void bufferFixed64NoTag(long l) {
            byte[] arrby = this.buffer;
            int n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)(l & 255L);
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)(l >> 8 & 255L);
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)(l >> 16 & 255L);
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)(255L & l >> 24);
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)((int)(l >> 32) & 255);
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)((int)(l >> 40) & 255);
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)((int)(l >> 48) & 255);
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)((int)(l >> 56) & 255);
            this.totalBytesWritten += 8;
        }

        final void bufferInt32NoTag(int n) {
            if (n >= 0) {
                this.bufferUInt32NoTag(n);
            } else {
                this.bufferUInt64NoTag(n);
            }
        }

        final void bufferTag(int n, int n2) {
            this.bufferUInt32NoTag(WireFormat.makeTag(n, n2));
        }

        final void bufferUInt32NoTag(int n) {
            int n2 = n;
            if (HAS_UNSAFE_ARRAY_OPERATIONS) {
                long l;
                long l2 = l = ARRAY_BASE_OFFSET + (long)this.position;
                do {
                    if ((n & -128) == 0) {
                        UNSAFE.putByte((Object)this.buffer, l2, (byte)n);
                        n = (int)(1L + l2 - l);
                        this.position += n;
                        this.totalBytesWritten += n;
                        return;
                    }
                    UNSAFE.putByte((Object)this.buffer, l2, (byte)(n & 127 | 128));
                    n >>>= 7;
                    l2 = 1L + l2;
                } while (true);
            }
            do {
                byte[] arrby;
                if ((n2 & -128) == 0) {
                    arrby = this.buffer;
                    n = this.position;
                    this.position = n + 1;
                    arrby[n] = (byte)n2;
                    ++this.totalBytesWritten;
                    return;
                }
                arrby = this.buffer;
                n = this.position;
                this.position = n + 1;
                arrby[n] = (byte)(n2 & 127 | 128);
                ++this.totalBytesWritten;
                n2 >>>= 7;
            } while (true);
        }

        final void bufferUInt64NoTag(long l) {
            if (HAS_UNSAFE_ARRAY_OPERATIONS) {
                long l2;
                long l3 = l2 = ARRAY_BASE_OFFSET + (long)this.position;
                long l4 = l;
                l = l3;
                do {
                    if ((l4 & -128L) == 0L) {
                        UNSAFE.putByte((Object)this.buffer, l, (byte)l4);
                        int n = (int)(1L + l - l2);
                        this.position += n;
                        this.totalBytesWritten += n;
                        return;
                    }
                    UNSAFE.putByte((Object)this.buffer, l, (byte)((int)l4 & 127 | 128));
                    l4 >>>= 7;
                    l = 1L + l;
                } while (true);
            }
            do {
                int n;
                byte[] arrby;
                if ((l & -128L) == 0L) {
                    arrby = this.buffer;
                    n = this.position;
                    this.position = n + 1;
                    arrby[n] = (byte)l;
                    ++this.totalBytesWritten;
                    return;
                }
                arrby = this.buffer;
                n = this.position;
                this.position = n + 1;
                arrby[n] = (byte)((int)l & 127 | 128);
                ++this.totalBytesWritten;
                l >>>= 7;
            } while (true);
        }

        @Override
        public final int getTotalBytesWritten() {
            return this.totalBytesWritten;
        }

        @Override
        public final int spaceLeft() {
            throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array or ByteBuffer.");
        }
    }

    private static class ArrayEncoder
    extends CodedOutputStream {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        ArrayEncoder(byte[] arrby, int n, int n2) {
            if (arrby != null) {
                if ((n | n2 | arrby.length - (n + n2)) >= 0) {
                    this.buffer = arrby;
                    this.offset = n;
                    this.position = n;
                    this.limit = n + n2;
                    return;
                }
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", arrby.length, n, n2));
            }
            throw new NullPointerException("buffer");
        }

        @Override
        public void flush() {
        }

        @Override
        public final int getTotalBytesWritten() {
            return this.position - this.offset;
        }

        @Override
        public final int spaceLeft() {
            return this.limit - this.position;
        }

        @Override
        public final void write(byte by) throws IOException {
            try {
                byte[] arrby = this.buffer;
                int n = this.position;
                this.position = n + 1;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new OutOfSpaceException(new IndexOutOfBoundsException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1)));
            }
            arrby[n] = by;
        }

        @Override
        public final void write(ByteBuffer byteBuffer) throws IOException {
            int n = byteBuffer.remaining();
            try {
                byteBuffer.get(this.buffer, this.position, n);
                this.position += n;
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new OutOfSpaceException(new IndexOutOfBoundsException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, n)));
            }
        }

        @Override
        public final void write(byte[] arrby, int n, int n2) throws IOException {
            try {
                System.arraycopy(arrby, n, this.buffer, this.position, n2);
                this.position += n2;
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new OutOfSpaceException(new IndexOutOfBoundsException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, n2)));
            }
        }

        @Override
        public final void writeBool(int n, boolean bl) throws IOException {
            this.writeTag(n, 0);
            this.write((byte)(bl ? 1 : 0));
        }

        @Override
        public final void writeByteArray(int n, byte[] arrby) throws IOException {
            this.writeByteArray(n, arrby, 0, arrby.length);
        }

        @Override
        public final void writeByteArray(int n, byte[] arrby, int n2, int n3) throws IOException {
            this.writeTag(n, 2);
            this.writeByteArrayNoTag(arrby, n2, n3);
        }

        @Override
        public final void writeByteArrayNoTag(byte[] arrby, int n, int n2) throws IOException {
            this.writeUInt32NoTag(n2);
            this.write(arrby, n, n2);
        }

        @Override
        public final void writeByteBuffer(int n, ByteBuffer byteBuffer) throws IOException {
            this.writeTag(n, 2);
            this.writeUInt32NoTag(byteBuffer.capacity());
            this.writeRawBytes(byteBuffer);
        }

        @Override
        public final void writeBytes(int n, ByteString byteString) throws IOException {
            this.writeTag(n, 2);
            this.writeBytesNoTag(byteString);
        }

        @Override
        public final void writeBytesNoTag(ByteString byteString) throws IOException {
            this.writeUInt32NoTag(byteString.size());
            byteString.writeTo(this);
        }

        @Override
        public final void writeFixed32(int n, int n2) throws IOException {
            this.writeTag(n, 5);
            this.writeFixed32NoTag(n2);
        }

        @Override
        public final void writeFixed32NoTag(int n) throws IOException {
            byte[] arrby;
            int n2;
            try {
                arrby = this.buffer;
                n2 = this.position;
                this.position = n2 + 1;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new OutOfSpaceException(new IndexOutOfBoundsException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1)));
            }
            arrby[n2] = (byte)(n & 255);
            arrby = this.buffer;
            n2 = this.position;
            this.position = n2 + 1;
            arrby[n2] = (byte)(n >> 8 & 255);
            arrby = this.buffer;
            n2 = this.position;
            this.position = n2 + 1;
            arrby[n2] = (byte)(n >> 16 & 255);
            arrby = this.buffer;
            n2 = this.position;
            this.position = n2 + 1;
            arrby[n2] = (byte)(n >> 24 & 255);
        }

        @Override
        public final void writeFixed64(int n, long l) throws IOException {
            this.writeTag(n, 1);
            this.writeFixed64NoTag(l);
        }

        @Override
        public final void writeFixed64NoTag(long l) throws IOException {
            byte[] arrby;
            int n;
            try {
                arrby = this.buffer;
                n = this.position;
                this.position = n + 1;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new OutOfSpaceException(new IndexOutOfBoundsException(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1)));
            }
            arrby[n] = (byte)((int)l & 255);
            arrby = this.buffer;
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)((int)(l >> 8) & 255);
            arrby = this.buffer;
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)((int)(l >> 16) & 255);
            arrby = this.buffer;
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)((int)(l >> 24) & 255);
            arrby = this.buffer;
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)((int)(l >> 32) & 255);
            arrby = this.buffer;
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)((int)(l >> 40) & 255);
            arrby = this.buffer;
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)((int)(l >> 48) & 255);
            arrby = this.buffer;
            n = this.position;
            this.position = n + 1;
            arrby[n] = (byte)((int)(l >> 56) & 255);
        }

        @Override
        public final void writeInt32(int n, int n2) throws IOException {
            this.writeTag(n, 0);
            this.writeInt32NoTag(n2);
        }

        @Override
        public final void writeInt32NoTag(int n) throws IOException {
            if (n >= 0) {
                this.writeUInt32NoTag(n);
            } else {
                this.writeUInt64NoTag(n);
            }
        }

        @Override
        public final void writeLazy(ByteBuffer byteBuffer) throws IOException {
            this.write(byteBuffer);
        }

        @Override
        public final void writeLazy(byte[] arrby, int n, int n2) throws IOException {
            this.write(arrby, n, n2);
        }

        @Override
        public final void writeMessage(int n, MessageLite messageLite) throws IOException {
            this.writeTag(n, 2);
            this.writeMessageNoTag(messageLite);
        }

        @Override
        public final void writeMessageNoTag(MessageLite messageLite) throws IOException {
            this.writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override
        public final void writeMessageSetExtension(int n, MessageLite messageLite) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, n);
            this.writeMessage(3, messageLite);
            this.writeTag(1, 4);
        }

        @Override
        public final void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                this.write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
            } else {
                byteBuffer = byteBuffer.duplicate();
                byteBuffer.clear();
                this.write(byteBuffer);
            }
        }

        @Override
        public final void writeRawMessageSetExtension(int n, ByteString byteString) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, n);
            this.writeBytes(3, byteString);
            this.writeTag(1, 4);
        }

        @Override
        public final void writeString(int n, String string2) throws IOException {
            this.writeTag(n, 2);
            this.writeStringNoTag(string2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final void writeStringNoTag(String string2) throws IOException {
            int n = this.position;
            try {
                int n2 = ArrayEncoder.computeUInt32SizeNoTag(string2.length() * 3);
                int n3 = ArrayEncoder.computeUInt32SizeNoTag(string2.length());
                if (n3 == n2) {
                    this.position = n + n3;
                    n2 = Utf8.encode(string2, this.buffer, this.position, this.spaceLeft());
                    this.position = n;
                    this.writeUInt32NoTag(n2 - n - n3);
                    this.position = n2;
                    return;
                }
                this.writeUInt32NoTag(Utf8.encodedLength(string2));
                this.position = Utf8.encode(string2, this.buffer, this.position, this.spaceLeft());
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new OutOfSpaceException(indexOutOfBoundsException);
            }
            catch (Utf8.UnpairedSurrogateException unpairedSurrogateException) {
                this.position = n;
                this.inefficientWriteStringNoTag(string2, unpairedSurrogateException);
            }
        }

        @Override
        public final void writeTag(int n, int n2) throws IOException {
            this.writeUInt32NoTag(WireFormat.makeTag(n, n2));
        }

        @Override
        public final void writeUInt32(int n, int n2) throws IOException {
            this.writeTag(n, 0);
            this.writeUInt32NoTag(n2);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public final void writeUInt32NoTag(int var1_1) throws IOException {
            var2_2 = var1_1;
            if (CodedOutputStream.access$100()) {
                var2_2 = var1_1;
                if (this.spaceLeft() >= 10) {
                    var3_3 = CodedOutputStream.access$200() + (long)this.position;
                    do {
                        if ((var1_1 & -128) == 0) {
                            CodedOutputStream.access$300().putByte((Object)this.buffer, var3_3, (byte)var1_1);
                            ++this.position;
                            return;
                        }
                        CodedOutputStream.access$300().putByte((Object)this.buffer, var3_3, (byte)(var1_1 & 127 | 128));
                        ++this.position;
                        var1_1 >>>= 7;
                        var3_3 = 1L + var3_3;
                    } while (true);
                }
            }
            do lbl-1000: // 2 sources:
            {
                if ((var2_2 & -128) == 0) {
                    var5_4 = this.buffer;
                    var1_1 = this.position;
                    this.position = var1_1 + 1;
                    var5_4[var1_1] = (byte)var2_2;
                    return;
                }
                var5_4 = this.buffer;
                var1_1 = this.position;
                this.position = var1_1 + 1;
                break;
            } while (true);
            catch (IndexOutOfBoundsException var5_5) {
                throw new OutOfSpaceException(new IndexOutOfBoundsException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{this.position, this.limit, 1})));
            }
            {
                var5_4[var1_1] = (byte)(var2_2 & 127 | 128);
                var2_2 >>>= 7;
                ** while (true)
            }
        }

        @Override
        public final void writeUInt64(int n, long l) throws IOException {
            this.writeTag(n, 0);
            this.writeUInt64NoTag(l);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public final void writeUInt64NoTag(long var1_1) throws IOException {
            var3_2 = var1_1;
            if (CodedOutputStream.access$100()) {
                var3_2 = var1_1;
                if (this.spaceLeft() >= 10) {
                    var5_3 = CodedOutputStream.access$200() + (long)this.position;
                    var3_2 = var1_1;
                    var1_1 = var5_3;
                    do {
                        if ((var3_2 & -128L) == 0L) {
                            CodedOutputStream.access$300().putByte((Object)this.buffer, var1_1, (byte)var3_2);
                            ++this.position;
                            return;
                        }
                        CodedOutputStream.access$300().putByte((Object)this.buffer, var1_1, (byte)((int)var3_2 & 127 | 128));
                        ++this.position;
                        var3_2 >>>= 7;
                        var1_1 = 1L + var1_1;
                    } while (true);
                }
            }
            do lbl-1000: // 2 sources:
            {
                if ((var3_2 & -128L) == 0L) {
                    var7_4 = this.buffer;
                    var8_6 = this.position;
                    this.position = var8_6 + 1;
                    var7_4[var8_6] = (byte)var3_2;
                    return;
                }
                var7_4 = this.buffer;
                var8_6 = this.position;
                this.position = var8_6 + 1;
                break;
            } while (true);
            catch (IndexOutOfBoundsException var7_5) {
                throw new OutOfSpaceException(new IndexOutOfBoundsException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{this.position, this.limit, 1})));
            }
            {
                var7_4[var8_6] = (byte)((int)var3_2 & 127 | 128);
                var3_2 >>>= 7;
                ** while (true)
            }
        }
    }

    private static final class ByteOutputEncoder
    extends AbstractBufferedEncoder {
        private final ByteOutput out;

        ByteOutputEncoder(ByteOutput byteOutput, int n) {
            super(n);
            if (byteOutput != null) {
                this.out = byteOutput;
                return;
            }
            throw new NullPointerException("out");
        }

        private void doFlush() throws IOException {
            this.out.write(this.buffer, 0, this.position);
            this.position = 0;
        }

        private void flushIfNotAvailable(int n) throws IOException {
            if (this.limit - this.position < n) {
                this.doFlush();
            }
        }

        @Override
        public void flush() throws IOException {
            if (this.position > 0) {
                this.doFlush();
            }
        }

        @Override
        public void write(byte by) throws IOException {
            if (this.position == this.limit) {
                this.doFlush();
            }
            this.buffer(by);
        }

        @Override
        public void write(ByteBuffer byteBuffer) throws IOException {
            this.flush();
            int n = byteBuffer.remaining();
            this.out.write(byteBuffer);
            this.totalBytesWritten += n;
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            this.flush();
            this.out.write(arrby, n, n2);
            this.totalBytesWritten += n2;
        }

        @Override
        public void writeBool(int n, boolean bl) throws IOException {
            this.flushIfNotAvailable(11);
            this.bufferTag(n, 0);
            this.buffer((byte)(bl ? 1 : 0));
        }

        @Override
        public void writeByteArray(int n, byte[] arrby) throws IOException {
            this.writeByteArray(n, arrby, 0, arrby.length);
        }

        @Override
        public void writeByteArray(int n, byte[] arrby, int n2, int n3) throws IOException {
            this.writeTag(n, 2);
            this.writeByteArrayNoTag(arrby, n2, n3);
        }

        @Override
        public void writeByteArrayNoTag(byte[] arrby, int n, int n2) throws IOException {
            this.writeUInt32NoTag(n2);
            this.write(arrby, n, n2);
        }

        @Override
        public void writeByteBuffer(int n, ByteBuffer byteBuffer) throws IOException {
            this.writeTag(n, 2);
            this.writeUInt32NoTag(byteBuffer.capacity());
            this.writeRawBytes(byteBuffer);
        }

        @Override
        public void writeBytes(int n, ByteString byteString) throws IOException {
            this.writeTag(n, 2);
            this.writeBytesNoTag(byteString);
        }

        @Override
        public void writeBytesNoTag(ByteString byteString) throws IOException {
            this.writeUInt32NoTag(byteString.size());
            byteString.writeTo(this);
        }

        @Override
        public void writeFixed32(int n, int n2) throws IOException {
            this.flushIfNotAvailable(14);
            this.bufferTag(n, 5);
            this.bufferFixed32NoTag(n2);
        }

        @Override
        public void writeFixed32NoTag(int n) throws IOException {
            this.flushIfNotAvailable(4);
            this.bufferFixed32NoTag(n);
        }

        @Override
        public void writeFixed64(int n, long l) throws IOException {
            this.flushIfNotAvailable(18);
            this.bufferTag(n, 1);
            this.bufferFixed64NoTag(l);
        }

        @Override
        public void writeFixed64NoTag(long l) throws IOException {
            this.flushIfNotAvailable(8);
            this.bufferFixed64NoTag(l);
        }

        @Override
        public void writeInt32(int n, int n2) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(n, 0);
            this.bufferInt32NoTag(n2);
        }

        @Override
        public void writeInt32NoTag(int n) throws IOException {
            if (n >= 0) {
                this.writeUInt32NoTag(n);
            } else {
                this.writeUInt64NoTag(n);
            }
        }

        @Override
        public void writeLazy(ByteBuffer byteBuffer) throws IOException {
            this.flush();
            int n = byteBuffer.remaining();
            this.out.writeLazy(byteBuffer);
            this.totalBytesWritten += n;
        }

        @Override
        public void writeLazy(byte[] arrby, int n, int n2) throws IOException {
            this.flush();
            this.out.writeLazy(arrby, n, n2);
            this.totalBytesWritten += n2;
        }

        @Override
        public void writeMessage(int n, MessageLite messageLite) throws IOException {
            this.writeTag(n, 2);
            this.writeMessageNoTag(messageLite);
        }

        @Override
        public void writeMessageNoTag(MessageLite messageLite) throws IOException {
            this.writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override
        public void writeMessageSetExtension(int n, MessageLite messageLite) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, n);
            this.writeMessage(3, messageLite);
            this.writeTag(1, 4);
        }

        @Override
        public void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                this.write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
            } else {
                byteBuffer = byteBuffer.duplicate();
                byteBuffer.clear();
                this.write(byteBuffer);
            }
        }

        @Override
        public void writeRawMessageSetExtension(int n, ByteString byteString) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, n);
            this.writeBytes(3, byteString);
            this.writeTag(1, 4);
        }

        @Override
        public void writeString(int n, String string2) throws IOException {
            this.writeTag(n, 2);
            this.writeStringNoTag(string2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void writeStringNoTag(String string2) throws IOException {
            int n = string2.length() * 3;
            int n2 = ByteOutputEncoder.computeUInt32SizeNoTag(n);
            if (n2 + n > this.limit) {
                byte[] arrby = new byte[n];
                n = Utf8.encode(string2, arrby, 0, n);
                this.writeUInt32NoTag(n);
                this.writeLazy(arrby, 0, n);
                return;
            }
            if (n2 + n > this.limit - this.position) {
                this.doFlush();
            }
            n = this.position;
            try {
                int n3 = ByteOutputEncoder.computeUInt32SizeNoTag(string2.length());
                if (n3 == n2) {
                    this.position = n + n3;
                    n2 = Utf8.encode(string2, this.buffer, this.position, this.limit - this.position);
                    this.position = n;
                    n3 = n2 - n - n3;
                    this.bufferUInt32NoTag(n3);
                    this.position = n2;
                    this.totalBytesWritten += n3;
                    return;
                }
                n3 = Utf8.encodedLength(string2);
                this.bufferUInt32NoTag(n3);
                this.position = Utf8.encode(string2, this.buffer, this.position, n3);
                this.totalBytesWritten += n3;
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new OutOfSpaceException(indexOutOfBoundsException);
            }
            catch (Utf8.UnpairedSurrogateException unpairedSurrogateException) {
                this.totalBytesWritten -= this.position - n;
                this.position = n;
                this.inefficientWriteStringNoTag(string2, unpairedSurrogateException);
            }
        }

        @Override
        public void writeTag(int n, int n2) throws IOException {
            this.writeUInt32NoTag(WireFormat.makeTag(n, n2));
        }

        @Override
        public void writeUInt32(int n, int n2) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(n, 0);
            this.bufferUInt32NoTag(n2);
        }

        @Override
        public void writeUInt32NoTag(int n) throws IOException {
            this.flushIfNotAvailable(10);
            this.bufferUInt32NoTag(n);
        }

        @Override
        public void writeUInt64(int n, long l) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(n, 0);
            this.bufferUInt64NoTag(l);
        }

        @Override
        public void writeUInt64NoTag(long l) throws IOException {
            this.flushIfNotAvailable(10);
            this.bufferUInt64NoTag(l);
        }
    }

    private static final class NioEncoder
    extends CodedOutputStream {
        private final ByteBuffer buffer;
        private final int initialPosition;
        private final ByteBuffer originalBuffer;

        NioEncoder(ByteBuffer byteBuffer) {
            this.originalBuffer = byteBuffer;
            this.buffer = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.initialPosition = byteBuffer.position();
        }

        private void encode(String string2) throws IOException {
            try {
                Utf8.encodeUtf8(string2, this.buffer);
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new OutOfSpaceException(indexOutOfBoundsException);
            }
        }

        @Override
        public void flush() {
            this.originalBuffer.position(this.buffer.position());
        }

        @Override
        public int getTotalBytesWritten() {
            return this.buffer.position() - this.initialPosition;
        }

        @Override
        public int spaceLeft() {
            return this.buffer.remaining();
        }

        @Override
        public void write(byte by) throws IOException {
            try {
                this.buffer.put(by);
                return;
            }
            catch (BufferOverflowException bufferOverflowException) {
                throw new OutOfSpaceException(bufferOverflowException);
            }
        }

        @Override
        public void write(ByteBuffer byteBuffer) throws IOException {
            try {
                this.buffer.put(byteBuffer);
                return;
            }
            catch (BufferOverflowException bufferOverflowException) {
                throw new OutOfSpaceException(bufferOverflowException);
            }
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            try {
                this.buffer.put(arrby, n, n2);
                return;
            }
            catch (BufferOverflowException bufferOverflowException) {
                throw new OutOfSpaceException(bufferOverflowException);
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new OutOfSpaceException(indexOutOfBoundsException);
            }
        }

        @Override
        public void writeBool(int n, boolean bl) throws IOException {
            this.writeTag(n, 0);
            this.write((byte)(bl ? 1 : 0));
        }

        @Override
        public void writeByteArray(int n, byte[] arrby) throws IOException {
            this.writeByteArray(n, arrby, 0, arrby.length);
        }

        @Override
        public void writeByteArray(int n, byte[] arrby, int n2, int n3) throws IOException {
            this.writeTag(n, 2);
            this.writeByteArrayNoTag(arrby, n2, n3);
        }

        @Override
        public void writeByteArrayNoTag(byte[] arrby, int n, int n2) throws IOException {
            this.writeUInt32NoTag(n2);
            this.write(arrby, n, n2);
        }

        @Override
        public void writeByteBuffer(int n, ByteBuffer byteBuffer) throws IOException {
            this.writeTag(n, 2);
            this.writeUInt32NoTag(byteBuffer.capacity());
            this.writeRawBytes(byteBuffer);
        }

        @Override
        public void writeBytes(int n, ByteString byteString) throws IOException {
            this.writeTag(n, 2);
            this.writeBytesNoTag(byteString);
        }

        @Override
        public void writeBytesNoTag(ByteString byteString) throws IOException {
            this.writeUInt32NoTag(byteString.size());
            byteString.writeTo(this);
        }

        @Override
        public void writeFixed32(int n, int n2) throws IOException {
            this.writeTag(n, 5);
            this.writeFixed32NoTag(n2);
        }

        @Override
        public void writeFixed32NoTag(int n) throws IOException {
            try {
                this.buffer.putInt(n);
                return;
            }
            catch (BufferOverflowException bufferOverflowException) {
                throw new OutOfSpaceException(bufferOverflowException);
            }
        }

        @Override
        public void writeFixed64(int n, long l) throws IOException {
            this.writeTag(n, 1);
            this.writeFixed64NoTag(l);
        }

        @Override
        public void writeFixed64NoTag(long l) throws IOException {
            try {
                this.buffer.putLong(l);
                return;
            }
            catch (BufferOverflowException bufferOverflowException) {
                throw new OutOfSpaceException(bufferOverflowException);
            }
        }

        @Override
        public void writeInt32(int n, int n2) throws IOException {
            this.writeTag(n, 0);
            this.writeInt32NoTag(n2);
        }

        @Override
        public void writeInt32NoTag(int n) throws IOException {
            if (n >= 0) {
                this.writeUInt32NoTag(n);
            } else {
                this.writeUInt64NoTag(n);
            }
        }

        @Override
        public void writeLazy(ByteBuffer byteBuffer) throws IOException {
            this.write(byteBuffer);
        }

        @Override
        public void writeLazy(byte[] arrby, int n, int n2) throws IOException {
            this.write(arrby, n, n2);
        }

        @Override
        public void writeMessage(int n, MessageLite messageLite) throws IOException {
            this.writeTag(n, 2);
            this.writeMessageNoTag(messageLite);
        }

        @Override
        public void writeMessageNoTag(MessageLite messageLite) throws IOException {
            this.writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override
        public void writeMessageSetExtension(int n, MessageLite messageLite) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, n);
            this.writeMessage(3, messageLite);
            this.writeTag(1, 4);
        }

        @Override
        public void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                this.write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
            } else {
                byteBuffer = byteBuffer.duplicate();
                byteBuffer.clear();
                this.write(byteBuffer);
            }
        }

        @Override
        public void writeRawMessageSetExtension(int n, ByteString byteString) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, n);
            this.writeBytes(3, byteString);
            this.writeTag(1, 4);
        }

        @Override
        public void writeString(int n, String string2) throws IOException {
            this.writeTag(n, 2);
            this.writeStringNoTag(string2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void writeStringNoTag(String string2) throws IOException {
            int n = this.buffer.position();
            try {
                int n2 = NioEncoder.computeUInt32SizeNoTag(string2.length() * 3);
                int n3 = NioEncoder.computeUInt32SizeNoTag(string2.length());
                if (n3 == n2) {
                    n3 = this.buffer.position() + n3;
                    this.buffer.position(n3);
                    this.encode(string2);
                    n2 = this.buffer.position();
                    this.buffer.position(n);
                    this.writeUInt32NoTag(n2 - n3);
                    this.buffer.position(n2);
                    return;
                }
                this.writeUInt32NoTag(Utf8.encodedLength(string2));
                this.encode(string2);
                return;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new OutOfSpaceException(illegalArgumentException);
            }
            catch (Utf8.UnpairedSurrogateException unpairedSurrogateException) {
                this.buffer.position(n);
                this.inefficientWriteStringNoTag(string2, unpairedSurrogateException);
            }
        }

        @Override
        public void writeTag(int n, int n2) throws IOException {
            this.writeUInt32NoTag(WireFormat.makeTag(n, n2));
        }

        @Override
        public void writeUInt32(int n, int n2) throws IOException {
            this.writeTag(n, 0);
            this.writeUInt32NoTag(n2);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void writeUInt32NoTag(int var1_1) throws IOException {
            do {
                if ((var1_1 & -128) != 0) ** GOTO lbl7
                try {
                    this.buffer.put((byte)var1_1);
                    return;
lbl7: // 1 sources:
                    this.buffer.put((byte)(var1_1 & 127 | 128));
                    var1_1 >>>= 7;
                }
                catch (BufferOverflowException var2_2) {
                    throw new OutOfSpaceException(var2_2);
                }
            } while (true);
        }

        @Override
        public void writeUInt64(int n, long l) throws IOException {
            this.writeTag(n, 0);
            this.writeUInt64NoTag(l);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void writeUInt64NoTag(long var1_1) throws IOException {
            do {
                if ((-128L & var1_1) != 0L) ** GOTO lbl7
                try {
                    this.buffer.put((byte)var1_1);
                    return;
lbl7: // 1 sources:
                    this.buffer.put((byte)((int)var1_1 & 127 | 128));
                    var1_1 >>>= 7;
                }
                catch (BufferOverflowException var3_2) {
                    throw new OutOfSpaceException(var3_2);
                }
            } while (true);
        }
    }

    private static final class NioHeapEncoder
    extends ArrayEncoder {
        private final ByteBuffer byteBuffer;
        private int initialPosition;

        NioHeapEncoder(ByteBuffer byteBuffer) {
            super(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            this.byteBuffer = byteBuffer;
            this.initialPosition = byteBuffer.position();
        }

        @Override
        public void flush() {
            this.byteBuffer.position(this.initialPosition + this.getTotalBytesWritten());
        }
    }

    public static class OutOfSpaceException
    extends IOException {
        private static final String MESSAGE = "CodedOutputStream was writing to a flat byte array and ran out of space.";
        private static final long serialVersionUID = -6947486886997889499L;

        OutOfSpaceException() {
            super(MESSAGE);
        }

        OutOfSpaceException(Throwable throwable) {
            super(MESSAGE, throwable);
        }
    }

    private static final class OutputStreamEncoder
    extends AbstractBufferedEncoder {
        private final OutputStream out;

        OutputStreamEncoder(OutputStream outputStream, int n) {
            super(n);
            if (outputStream != null) {
                this.out = outputStream;
                return;
            }
            throw new NullPointerException("out");
        }

        private void doFlush() throws IOException {
            this.out.write(this.buffer, 0, this.position);
            this.position = 0;
        }

        private void flushIfNotAvailable(int n) throws IOException {
            if (this.limit - this.position < n) {
                this.doFlush();
            }
        }

        @Override
        public void flush() throws IOException {
            if (this.position > 0) {
                this.doFlush();
            }
        }

        @Override
        public void write(byte by) throws IOException {
            if (this.position == this.limit) {
                this.doFlush();
            }
            this.buffer(by);
        }

        @Override
        public void write(ByteBuffer byteBuffer) throws IOException {
            int n = byteBuffer.remaining();
            if (this.limit - this.position >= n) {
                byteBuffer.get(this.buffer, this.position, n);
                this.position += n;
                this.totalBytesWritten += n;
            } else {
                int n2 = this.limit - this.position;
                byteBuffer.get(this.buffer, this.position, n2);
                n -= n2;
                this.position = this.limit;
                this.totalBytesWritten += n2;
                this.doFlush();
                while (n > this.limit) {
                    byteBuffer.get(this.buffer, 0, this.limit);
                    this.out.write(this.buffer, 0, this.limit);
                    n -= this.limit;
                    this.totalBytesWritten += this.limit;
                }
                byteBuffer.get(this.buffer, 0, n);
                this.position = n;
                this.totalBytesWritten += n;
            }
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            if (this.limit - this.position >= n2) {
                System.arraycopy(arrby, n, this.buffer, this.position, n2);
                this.position += n2;
                this.totalBytesWritten += n2;
            } else {
                int n3 = this.limit - this.position;
                System.arraycopy(arrby, n, this.buffer, this.position, n3);
                n += n3;
                this.position = this.limit;
                this.totalBytesWritten += n3;
                this.doFlush();
                if ((n2 -= n3) <= this.limit) {
                    System.arraycopy(arrby, n, this.buffer, 0, n2);
                    this.position = n2;
                } else {
                    this.out.write(arrby, n, n2);
                }
                this.totalBytesWritten += n2;
            }
        }

        @Override
        public void writeBool(int n, boolean bl) throws IOException {
            this.flushIfNotAvailable(11);
            this.bufferTag(n, 0);
            this.buffer((byte)(bl ? 1 : 0));
        }

        @Override
        public void writeByteArray(int n, byte[] arrby) throws IOException {
            this.writeByteArray(n, arrby, 0, arrby.length);
        }

        @Override
        public void writeByteArray(int n, byte[] arrby, int n2, int n3) throws IOException {
            this.writeTag(n, 2);
            this.writeByteArrayNoTag(arrby, n2, n3);
        }

        @Override
        public void writeByteArrayNoTag(byte[] arrby, int n, int n2) throws IOException {
            this.writeUInt32NoTag(n2);
            this.write(arrby, n, n2);
        }

        @Override
        public void writeByteBuffer(int n, ByteBuffer byteBuffer) throws IOException {
            this.writeTag(n, 2);
            this.writeUInt32NoTag(byteBuffer.capacity());
            this.writeRawBytes(byteBuffer);
        }

        @Override
        public void writeBytes(int n, ByteString byteString) throws IOException {
            this.writeTag(n, 2);
            this.writeBytesNoTag(byteString);
        }

        @Override
        public void writeBytesNoTag(ByteString byteString) throws IOException {
            this.writeUInt32NoTag(byteString.size());
            byteString.writeTo(this);
        }

        @Override
        public void writeFixed32(int n, int n2) throws IOException {
            this.flushIfNotAvailable(14);
            this.bufferTag(n, 5);
            this.bufferFixed32NoTag(n2);
        }

        @Override
        public void writeFixed32NoTag(int n) throws IOException {
            this.flushIfNotAvailable(4);
            this.bufferFixed32NoTag(n);
        }

        @Override
        public void writeFixed64(int n, long l) throws IOException {
            this.flushIfNotAvailable(18);
            this.bufferTag(n, 1);
            this.bufferFixed64NoTag(l);
        }

        @Override
        public void writeFixed64NoTag(long l) throws IOException {
            this.flushIfNotAvailable(8);
            this.bufferFixed64NoTag(l);
        }

        @Override
        public void writeInt32(int n, int n2) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(n, 0);
            this.bufferInt32NoTag(n2);
        }

        @Override
        public void writeInt32NoTag(int n) throws IOException {
            if (n >= 0) {
                this.writeUInt32NoTag(n);
            } else {
                this.writeUInt64NoTag(n);
            }
        }

        @Override
        public void writeLazy(ByteBuffer byteBuffer) throws IOException {
            this.write(byteBuffer);
        }

        @Override
        public void writeLazy(byte[] arrby, int n, int n2) throws IOException {
            this.write(arrby, n, n2);
        }

        @Override
        public void writeMessage(int n, MessageLite messageLite) throws IOException {
            this.writeTag(n, 2);
            this.writeMessageNoTag(messageLite);
        }

        @Override
        public void writeMessageNoTag(MessageLite messageLite) throws IOException {
            this.writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override
        public void writeMessageSetExtension(int n, MessageLite messageLite) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, n);
            this.writeMessage(3, messageLite);
            this.writeTag(1, 4);
        }

        @Override
        public void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                this.write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
            } else {
                byteBuffer = byteBuffer.duplicate();
                byteBuffer.clear();
                this.write(byteBuffer);
            }
        }

        @Override
        public void writeRawMessageSetExtension(int n, ByteString byteString) throws IOException {
            this.writeTag(1, 3);
            this.writeUInt32(2, n);
            this.writeBytes(3, byteString);
            this.writeTag(1, 4);
        }

        @Override
        public void writeString(int n, String string2) throws IOException {
            this.writeTag(n, 2);
            this.writeStringNoTag(string2);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void writeStringNoTag(String var1_1) throws IOException {
            var2_2 = var1_1.length() * 3;
            var3_3 = OutputStreamEncoder.computeUInt32SizeNoTag(var2_2);
            if (var3_3 + var2_2 > this.limit) {
                var4_4 = new byte[var2_2];
                var3_3 = Utf8.encode(var1_1, var4_4, 0, var2_2);
                this.writeUInt32NoTag(var3_3);
                this.writeLazy(var4_4, 0, var3_3);
                return;
            }
            if (var3_3 + var2_2 > this.limit - this.position) {
                this.doFlush();
            }
            var5_8 = OutputStreamEncoder.computeUInt32SizeNoTag(var1_1.length());
            var2_2 = this.position;
            if (var5_8 != var3_3) ** GOTO lbl23
            try {
                block9 : {
                    this.position = var2_2 + var5_8;
                    var6_9 = Utf8.encode(var1_1, this.buffer, this.position, this.limit - this.position);
                    this.position = var2_2;
                    var3_3 = var6_9 - var2_2 - var5_8;
                    this.bufferUInt32NoTag(var3_3);
                    this.position = var6_9;
                    break block9;
lbl23: // 1 sources:
                    var3_3 = Utf8.encodedLength(var1_1);
                    this.bufferUInt32NoTag(var3_3);
                    this.position = Utf8.encode(var1_1, this.buffer, this.position, var3_3);
                }
                this.totalBytesWritten += var3_3;
                return;
            }
            catch (ArrayIndexOutOfBoundsException var4_5) {
                try {
                    var7_10 = new OutOfSpaceException(var4_5);
                    throw var7_10;
                    catch (Utf8.UnpairedSurrogateException var4_6) {
                        this.totalBytesWritten -= this.position - var2_2;
                        this.position = var2_2;
                        throw var4_6;
                    }
                }
                catch (Utf8.UnpairedSurrogateException var4_7) {
                    this.inefficientWriteStringNoTag(var1_1, var4_7);
                }
            }
        }

        @Override
        public void writeTag(int n, int n2) throws IOException {
            this.writeUInt32NoTag(WireFormat.makeTag(n, n2));
        }

        @Override
        public void writeUInt32(int n, int n2) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(n, 0);
            this.bufferUInt32NoTag(n2);
        }

        @Override
        public void writeUInt32NoTag(int n) throws IOException {
            this.flushIfNotAvailable(10);
            this.bufferUInt32NoTag(n);
        }

        @Override
        public void writeUInt64(int n, long l) throws IOException {
            this.flushIfNotAvailable(20);
            this.bufferTag(n, 0);
            this.bufferUInt64NoTag(l);
        }

        @Override
        public void writeUInt64NoTag(long l) throws IOException {
            this.flushIfNotAvailable(10);
            this.bufferUInt64NoTag(l);
        }
    }

}

