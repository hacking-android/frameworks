/*
 * Decompiled with CFR 0.145.
 */
package android.util.proto;

import android.util.proto.ProtoParseException;
import android.util.proto.ProtoStream;
import android.util.proto.WireTypeMismatchException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public final class ProtoInputStream
extends ProtoStream {
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int NO_MORE_FIELDS = -1;
    private static final byte STATE_FIELD_MISS = 4;
    private static final byte STATE_READING_PACKED = 2;
    private static final byte STATE_STARTED_FIELD_READ = 1;
    private byte[] mBuffer;
    private final int mBufferSize;
    private int mDepth = -1;
    private int mDiscardedBytes = 0;
    private int mEnd = 0;
    private ArrayList<Long> mExpectedObjectTokenStack = null;
    private int mFieldNumber;
    private int mOffset = 0;
    private int mPackedEnd = 0;
    private byte mState = (byte)(false ? 1 : 0);
    private InputStream mStream;
    private int mWireType;

    public ProtoInputStream(InputStream inputStream) {
        this(inputStream, 8192);
    }

    public ProtoInputStream(InputStream inputStream, int n) {
        this.mStream = inputStream;
        this.mBufferSize = n > 0 ? n : 8192;
        this.mBuffer = new byte[this.mBufferSize];
    }

    public ProtoInputStream(byte[] arrby) {
        this.mBufferSize = arrby.length;
        this.mEnd = arrby.length;
        this.mBuffer = arrby;
        this.mStream = null;
    }

    private void assertFieldNumber(long l) {
        if ((int)l == this.mFieldNumber) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested field id (");
        stringBuilder.append(ProtoInputStream.getFieldIdString(l));
        stringBuilder.append(") does not match current field number (0x");
        stringBuilder.append(Integer.toHexString(this.mFieldNumber));
        stringBuilder.append(") at offset 0x");
        stringBuilder.append(Integer.toHexString(this.getOffset()));
        stringBuilder.append(this.dumpDebugData());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void assertFreshData() {
        if ((this.mState & 1) == 1) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempting to read already read field at offset 0x");
        stringBuilder.append(Integer.toHexString(this.getOffset()));
        stringBuilder.append(this.dumpDebugData());
        throw new ProtoParseException(stringBuilder.toString());
    }

    private void assertWireType(int n) {
        if (n == this.mWireType) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Current wire type ");
        stringBuilder.append(ProtoInputStream.getWireTypeString(this.mWireType));
        stringBuilder.append(" does not match expected wire type ");
        stringBuilder.append(ProtoInputStream.getWireTypeString(n));
        stringBuilder.append(" at offset 0x");
        stringBuilder.append(Integer.toHexString(this.getOffset()));
        stringBuilder.append(this.dumpDebugData());
        throw new WireTypeMismatchException(stringBuilder.toString());
    }

    private void checkPacked(long l) throws IOException {
        if (this.mWireType == 2) {
            int n = (int)this.readVarint();
            this.mPackedEnd = this.getOffset() + n;
            this.mState = (byte)(2 | this.mState);
            switch ((int)((0xFF00000000L & l) >>> 32)) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Requested field id (");
                    stringBuilder.append(ProtoInputStream.getFieldIdString(l));
                    stringBuilder.append(") is not a packable field");
                    stringBuilder.append(this.dumpDebugData());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                case 3: 
                case 4: 
                case 5: 
                case 8: 
                case 13: 
                case 14: 
                case 17: 
                case 18: {
                    this.mWireType = 0;
                    break;
                }
                case 2: 
                case 7: 
                case 15: {
                    if (n % 4 == 0) {
                        this.mWireType = 5;
                        break;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Requested field id (");
                    stringBuilder.append(ProtoInputStream.getFieldIdString(l));
                    stringBuilder.append(") packed length ");
                    stringBuilder.append(n);
                    stringBuilder.append(" is not aligned for fixed32");
                    stringBuilder.append(this.dumpDebugData());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                case 1: 
                case 6: 
                case 16: {
                    if (n % 8 == 0) {
                        this.mWireType = 1;
                        break;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Requested field id (");
                    stringBuilder.append(ProtoInputStream.getFieldIdString(l));
                    stringBuilder.append(") packed length ");
                    stringBuilder.append(n);
                    stringBuilder.append(" is not aligned for fixed64");
                    stringBuilder.append(this.dumpDebugData());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
        }
    }

    private void fillBuffer() throws IOException {
        InputStream inputStream;
        int n = this.mOffset;
        int n2 = this.mEnd;
        if (n >= n2 && (inputStream = this.mStream) != null) {
            this.mOffset = n - n2;
            this.mDiscardedBytes += n2;
            n2 = this.mOffset;
            n = this.mBufferSize;
            if (n2 >= n) {
                n = (int)inputStream.skip(n2 / n * n);
                this.mDiscardedBytes += n;
                this.mOffset -= n;
            }
            this.mEnd = this.mStream.read(this.mBuffer);
        }
    }

    private void incOffset(int n) {
        this.mOffset += n;
        if (this.mDepth >= 0 && this.getOffset() > ProtoInputStream.getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth))) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpectedly reached end of embedded object.  ");
            stringBuilder.append(ProtoInputStream.token2String(this.mExpectedObjectTokenStack.get(this.mDepth)));
            stringBuilder.append(this.dumpDebugData());
            throw new ProtoParseException(stringBuilder.toString());
        }
    }

    private int readFixed32() throws IOException {
        if (this.mOffset + 4 <= this.mEnd) {
            this.incOffset(4);
            byte[] arrby = this.mBuffer;
            int n = this.mOffset;
            byte by = arrby[n - 4];
            byte by2 = arrby[n - 3];
            byte by3 = arrby[n - 2];
            return (arrby[n - 1] & 255) << 24 | (by & 255 | (by2 & 255) << 8 | (by3 & 255) << 16);
        }
        int n = 0;
        int n2 = 0;
        int n3 = 4;
        while (n3 > 0) {
            this.fillBuffer();
            int n4 = this.mEnd;
            int n5 = this.mOffset;
            n5 = n4 - n5 < n3 ? n4 - n5 : n3;
            this.incOffset(n5);
            n3 -= n5;
            while (n5 > 0) {
                n |= (this.mBuffer[this.mOffset - n5] & 255) << n2;
                --n5;
                n2 += 8;
            }
        }
        return n;
    }

    private long readFixed64() throws IOException {
        if (this.mOffset + 8 <= this.mEnd) {
            this.incOffset(8);
            byte[] arrby = this.mBuffer;
            int n = this.mOffset;
            long l = arrby[n - 8];
            long l2 = arrby[n - 7];
            long l3 = arrby[n - 6];
            long l4 = arrby[n - 5];
            long l5 = arrby[n - 4];
            long l6 = arrby[n - 3];
            long l7 = arrby[n - 2];
            return ((long)arrby[n - 1] & 255L) << 56 | (l & 255L | (l2 & 255L) << 8 | (l3 & 255L) << 16 | (l4 & 255L) << 24 | (l5 & 255L) << 32 | (l6 & 255L) << 40 | (l7 & 255L) << 48);
        }
        long l = 0L;
        int n = 0;
        int n2 = 8;
        while (n2 > 0) {
            this.fillBuffer();
            int n3 = this.mEnd;
            int n4 = this.mOffset;
            n4 = n3 - n4 < n2 ? n3 - n4 : n2;
            this.incOffset(n4);
            n2 -= n4;
            while (n4 > 0) {
                l |= ((long)this.mBuffer[this.mOffset - n4] & 255L) << n;
                --n4;
                n += 8;
            }
        }
        return l;
    }

    private byte[] readRawBytes(int n) throws IOException {
        int n2;
        int n3;
        Object object = new byte[n];
        int n4 = 0;
        while ((n3 = this.mOffset) + n - n4 > (n2 = this.mEnd)) {
            int n5 = n2 - n3;
            n2 = n4;
            if (n5 > 0) {
                System.arraycopy(this.mBuffer, n3, object, n4, n5);
                this.incOffset(n5);
                n2 = n4 + n5;
            }
            this.fillBuffer();
            if (this.mOffset < this.mEnd) {
                n4 = n2;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpectedly reached end of the InputStream at offset 0x");
            ((StringBuilder)object).append(Integer.toHexString(this.mEnd));
            ((StringBuilder)object).append(this.dumpDebugData());
            throw new ProtoParseException(((StringBuilder)object).toString());
        }
        System.arraycopy(this.mBuffer, n3, object, n4, n - n4);
        this.incOffset(n - n4);
        return object;
    }

    private String readRawString(int n) throws IOException {
        this.fillBuffer();
        int n2 = this.mOffset;
        int n3 = this.mEnd;
        if (n2 + n <= n3) {
            String string2 = new String(this.mBuffer, n2, n, StandardCharsets.UTF_8);
            this.incOffset(n);
            return string2;
        }
        if (n <= this.mBufferSize) {
            Object object = this.mBuffer;
            System.arraycopy(object, n2, object, 0, n3 -= n2);
            this.mEnd = this.mStream.read(this.mBuffer, n3, n - n3) + n3;
            this.mDiscardedBytes += this.mOffset;
            this.mOffset = 0;
            object = new String(this.mBuffer, this.mOffset, n, StandardCharsets.UTF_8);
            this.incOffset(n);
            return object;
        }
        return new String(this.readRawBytes(n), 0, n, StandardCharsets.UTF_8);
    }

    private void readTag() throws IOException {
        this.fillBuffer();
        if (this.mOffset >= this.mEnd) {
            this.mFieldNumber = -1;
            return;
        }
        int n = (int)this.readVarint();
        this.mFieldNumber = n >>> 3;
        this.mWireType = n & 7;
        this.mState = (byte)(this.mState | 1);
    }

    private long readVarint() throws IOException {
        long l = 0L;
        int n = 0;
        do {
            this.fillBuffer();
            int n2 = this.mEnd - this.mOffset;
            for (int i = 0; i < n2; ++i) {
                byte by = this.mBuffer[this.mOffset + i];
                l |= ((long)by & 127L) << n;
                if ((by & 128) == 0) {
                    this.incOffset(i + 1);
                    return l;
                }
                if ((n += 7) <= 63) {
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Varint is too large at offset 0x");
                stringBuilder.append(Integer.toHexString(this.getOffset() + i));
                stringBuilder.append(this.dumpDebugData());
                throw new ProtoParseException(stringBuilder.toString());
            }
            this.incOffset(n2);
        } while (true);
    }

    public int decodeZigZag32(int n) {
        return n >>> 1 ^ -(n & 1);
    }

    public long decodeZigZag64(long l) {
        return l >>> 1 ^ -(1L & l);
    }

    public String dumpDebugData() {
        StringBuilder stringBuilder = new StringBuilder();
        Serializable serializable = new StringBuilder();
        ((StringBuilder)serializable).append("\nmFieldNumber : 0x");
        ((StringBuilder)serializable).append(Integer.toHexString(this.mFieldNumber));
        stringBuilder.append(((StringBuilder)serializable).toString());
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("\nmWireType : 0x");
        ((StringBuilder)serializable).append(Integer.toHexString(this.mWireType));
        stringBuilder.append(((StringBuilder)serializable).toString());
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("\nmState : 0x");
        ((StringBuilder)serializable).append(Integer.toHexString(this.mState));
        stringBuilder.append(((StringBuilder)serializable).toString());
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("\nmDiscardedBytes : 0x");
        ((StringBuilder)serializable).append(Integer.toHexString(this.mDiscardedBytes));
        stringBuilder.append(((StringBuilder)serializable).toString());
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("\nmOffset : 0x");
        ((StringBuilder)serializable).append(Integer.toHexString(this.mOffset));
        stringBuilder.append(((StringBuilder)serializable).toString());
        stringBuilder.append("\nmExpectedObjectTokenStack : ");
        serializable = this.mExpectedObjectTokenStack;
        if (serializable == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(serializable);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("\nmDepth : 0x");
        ((StringBuilder)serializable).append(Integer.toHexString(this.mDepth));
        stringBuilder.append(((StringBuilder)serializable).toString());
        stringBuilder.append("\nmBuffer : ");
        serializable = this.mBuffer;
        if (serializable == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(serializable);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("\nmBufferSize : 0x");
        ((StringBuilder)serializable).append(Integer.toHexString(this.mBufferSize));
        stringBuilder.append(((StringBuilder)serializable).toString());
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("\nmEnd : 0x");
        ((StringBuilder)serializable).append(Integer.toHexString(this.mEnd));
        stringBuilder.append(((StringBuilder)serializable).toString());
        return stringBuilder.toString();
    }

    public void end(long l) {
        if (this.mExpectedObjectTokenStack.get(this.mDepth) == l) {
            if (ProtoInputStream.getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth)) > this.getOffset()) {
                this.incOffset(ProtoInputStream.getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth)) - this.getOffset());
            }
            --this.mDepth;
            this.mState = (byte)(this.mState & -2);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("end token ");
        stringBuilder.append(l);
        stringBuilder.append(" does not match current message token ");
        stringBuilder.append(this.mExpectedObjectTokenStack.get(this.mDepth));
        stringBuilder.append(this.dumpDebugData());
        throw new ProtoParseException(stringBuilder.toString());
    }

    public int getFieldNumber() {
        return this.mFieldNumber;
    }

    public int getOffset() {
        return this.mOffset + this.mDiscardedBytes;
    }

    public int getWireType() {
        if ((this.mState & 2) == 2) {
            return 2;
        }
        return this.mWireType;
    }

    public boolean isNextField(long l) throws IOException {
        if (this.nextField() == (int)l) {
            return true;
        }
        this.mState = (byte)(this.mState | 4);
        return false;
    }

    public int nextField() throws IOException {
        byte by = this.mState;
        if ((by & 4) == 4) {
            this.mState = (byte)(by & -5);
            return this.mFieldNumber;
        }
        if ((by & 1) == 1) {
            this.skip();
            this.mState = (byte)(this.mState & -2);
        }
        if ((this.mState & 2) == 2) {
            if (this.getOffset() < this.mPackedEnd) {
                this.mState = (byte)(this.mState | 1);
                return this.mFieldNumber;
            }
            if (this.getOffset() == this.mPackedEnd) {
                this.mState = (byte)(this.mState & -3);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpectedly reached end of packed field at offset 0x");
                stringBuilder.append(Integer.toHexString(this.mPackedEnd));
                stringBuilder.append(this.dumpDebugData());
                throw new ProtoParseException(stringBuilder.toString());
            }
        }
        if (this.mDepth >= 0 && this.getOffset() == ProtoInputStream.getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth))) {
            this.mFieldNumber = -1;
        } else {
            this.readTag();
        }
        return this.mFieldNumber;
    }

    public boolean readBoolean(long l) throws IOException {
        this.assertFreshData();
        this.assertFieldNumber(l);
        this.checkPacked(l);
        if ((int)((0xFF00000000L & l) >>> 32) == 8) {
            boolean bl = false;
            this.assertWireType(0);
            if (this.readVarint() != 0L) {
                bl = true;
            }
            this.mState = (byte)(this.mState & -2);
            return bl;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested field id (");
        stringBuilder.append(ProtoInputStream.getFieldIdString(l));
        stringBuilder.append(") is not an boolean");
        stringBuilder.append(this.dumpDebugData());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public byte[] readBytes(long l) throws IOException {
        this.assertFreshData();
        this.assertFieldNumber(l);
        int n = (int)((0xFF00000000L & l) >>> 32);
        if (n != 11 && n != 12) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested field type (");
            stringBuilder.append(ProtoInputStream.getFieldIdString(l));
            stringBuilder.append(") cannot be read as raw bytes");
            stringBuilder.append(this.dumpDebugData());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.assertWireType(2);
        byte[] arrby = this.readRawBytes((int)this.readVarint());
        this.mState = (byte)(this.mState & -2);
        return arrby;
    }

    public double readDouble(long l) throws IOException {
        this.assertFreshData();
        this.assertFieldNumber(l);
        this.checkPacked(l);
        if ((int)((0xFF00000000L & l) >>> 32) == 1) {
            this.assertWireType(1);
            double d = Double.longBitsToDouble(this.readFixed64());
            this.mState = (byte)(this.mState & -2);
            return d;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested field id (");
        stringBuilder.append(ProtoInputStream.getFieldIdString(l));
        stringBuilder.append(") cannot be read as a double");
        stringBuilder.append(this.dumpDebugData());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public float readFloat(long l) throws IOException {
        this.assertFreshData();
        this.assertFieldNumber(l);
        this.checkPacked(l);
        if ((int)((0xFF00000000L & l) >>> 32) == 2) {
            this.assertWireType(5);
            float f = Float.intBitsToFloat(this.readFixed32());
            this.mState = (byte)(this.mState & -2);
            return f;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested field id (");
        stringBuilder.append(ProtoInputStream.getFieldIdString(l));
        stringBuilder.append(") is not a float");
        stringBuilder.append(this.dumpDebugData());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public int readInt(long var1_1) throws IOException {
        block4 : {
            this.assertFreshData();
            this.assertFieldNumber(var1_1);
            this.checkPacked(var1_1);
            var3_2 = (int)((0xFF00000000L & var1_1) >>> 32);
            if (var3_2 == 5) break block4;
            if (var3_2 == 7) ** GOTO lbl-1000
            if (var3_2 == 17) ** GOTO lbl20
            switch (var3_2) {
                default: {
                    var4_3 = new StringBuilder();
                    var4_3.append("Requested field id (");
                    var4_3.append(ProtoInputStream.getFieldIdString(var1_1));
                    var4_3.append(") is not an int");
                    var4_3.append(this.dumpDebugData());
                    throw new IllegalArgumentException(var4_3.toString());
                }
lbl20: // 1 sources:
                this.assertWireType(0);
                var3_2 = this.decodeZigZag32((int)this.readVarint());
                ** break;
                case 15: lbl-1000: // 2 sources:
                {
                    this.assertWireType(5);
                    var3_2 = this.readFixed32();
                    ** break;
                }
                case 13: 
                case 14: 
            }
        }
        this.assertWireType(0);
        var3_2 = (int)this.readVarint();
lbl31: // 3 sources:
        this.mState = (byte)(this.mState & -2);
        return var3_2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public long readLong(long l) throws IOException {
        this.assertFreshData();
        this.assertFieldNumber(l);
        this.checkPacked(l);
        int n = (int)((0xFF00000000L & l) >>> 32);
        if (n != 3 && n != 4) {
            if (n != 6 && n != 16) {
                if (n != 18) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Requested field id (");
                    stringBuilder.append(ProtoInputStream.getFieldIdString(l));
                    stringBuilder.append(") is not an long");
                    stringBuilder.append(this.dumpDebugData());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                this.assertWireType(0);
                l = this.decodeZigZag64(this.readVarint());
            } else {
                this.assertWireType(1);
                l = this.readFixed64();
            }
        } else {
            this.assertWireType(0);
            l = this.readVarint();
        }
        this.mState = (byte)(this.mState & -2);
        return l;
    }

    public String readString(long l) throws IOException {
        this.assertFreshData();
        this.assertFieldNumber(l);
        if ((int)((0xFF00000000L & l) >>> 32) == 9) {
            this.assertWireType(2);
            String string2 = this.readRawString((int)this.readVarint());
            this.mState = (byte)(this.mState & -2);
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested field id(");
        stringBuilder.append(ProtoInputStream.getFieldIdString(l));
        stringBuilder.append(") is not an string");
        stringBuilder.append(this.dumpDebugData());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public void skip() throws IOException {
        if ((this.mState & 2) == 2) {
            this.incOffset(this.mPackedEnd - this.getOffset());
        } else {
            int n = this.mWireType;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 5) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unexpected wire type: ");
                            stringBuilder.append(this.mWireType);
                            stringBuilder.append(" at offset 0x");
                            stringBuilder.append(Integer.toHexString(this.mOffset));
                            stringBuilder.append(this.dumpDebugData());
                            throw new ProtoParseException(stringBuilder.toString());
                        }
                        this.incOffset(4);
                    } else {
                        this.fillBuffer();
                        this.incOffset((int)this.readVarint());
                    }
                } else {
                    this.incOffset(8);
                }
            } else {
                do {
                    this.fillBuffer();
                    n = this.mBuffer[this.mOffset];
                    this.incOffset(1);
                } while ((n & 128) != 0);
            }
        }
        this.mState = (byte)(this.mState & -2);
    }

    public long start(long l) throws IOException {
        Serializable serializable;
        int n;
        this.assertFreshData();
        this.assertFieldNumber(l);
        this.assertWireType(2);
        int n2 = (int)this.readVarint();
        if (this.mExpectedObjectTokenStack == null) {
            this.mExpectedObjectTokenStack = new ArrayList();
        }
        this.mDepth = n = this.mDepth + 1;
        if (n == this.mExpectedObjectTokenStack.size()) {
            serializable = this.mExpectedObjectTokenStack;
            boolean bl = (l & 0x20000000000L) == 0x20000000000L;
            ((ArrayList)serializable).add(ProtoInputStream.makeToken(0, bl, this.mDepth, (int)l, this.getOffset() + n2));
        } else {
            serializable = this.mExpectedObjectTokenStack;
            n = this.mDepth;
            boolean bl = (l & 0x20000000000L) == 0x20000000000L;
            ((ArrayList)serializable).set(n, ProtoInputStream.makeToken(0, bl, this.mDepth, (int)l, this.getOffset() + n2));
        }
        n2 = this.mDepth;
        if (n2 > 0 && ProtoInputStream.getOffsetFromToken(this.mExpectedObjectTokenStack.get(n2)) > ProtoInputStream.getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth - 1))) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Embedded Object (");
            ((StringBuilder)serializable).append(ProtoInputStream.token2String(this.mExpectedObjectTokenStack.get(this.mDepth)));
            ((StringBuilder)serializable).append(") ends after of parent Objects's (");
            ((StringBuilder)serializable).append(ProtoInputStream.token2String(this.mExpectedObjectTokenStack.get(this.mDepth - 1)));
            ((StringBuilder)serializable).append(") end");
            ((StringBuilder)serializable).append(this.dumpDebugData());
            throw new ProtoParseException(((StringBuilder)serializable).toString());
        }
        this.mState = (byte)(this.mState & -2);
        return this.mExpectedObjectTokenStack.get(this.mDepth);
    }
}

