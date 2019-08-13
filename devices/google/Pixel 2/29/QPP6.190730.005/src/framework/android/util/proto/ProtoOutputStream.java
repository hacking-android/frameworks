/*
 * Decompiled with CFR 0.145.
 */
package android.util.proto;

import android.util.Log;
import android.util.proto.EncodedBuffer;
import android.util.proto.ProtoParseException;
import android.util.proto.ProtoStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public final class ProtoOutputStream
extends ProtoStream {
    public static final String TAG = "ProtoOutputStream";
    private EncodedBuffer mBuffer;
    private boolean mCompacted;
    private int mCopyBegin;
    private int mDepth;
    private long mExpectedObjectToken;
    private int mNextObjectId = -1;
    private OutputStream mStream;

    public ProtoOutputStream() {
        this(0);
    }

    public ProtoOutputStream(int n) {
        this.mBuffer = new EncodedBuffer(n);
    }

    public ProtoOutputStream(FileDescriptor fileDescriptor) {
        this(new FileOutputStream(fileDescriptor));
    }

    public ProtoOutputStream(OutputStream outputStream) {
        this();
        this.mStream = outputStream;
    }

    private void assertNotCompacted() {
        if (!this.mCompacted) {
            return;
        }
        throw new IllegalArgumentException("write called after compact");
    }

    public static int checkFieldId(long l, long l2) {
        long l3 = l & 0xF0000000000L;
        long l4 = l & 0xFF00000000L;
        long l5 = l2 & 0xF0000000000L;
        l2 &= 0xFF00000000L;
        if ((int)l != 0) {
            if (l4 == l2 && (l3 == l5 || l3 == 0x50000000000L && l5 == 0x20000000000L)) {
                return (int)l;
            }
            String string2 = ProtoOutputStream.getFieldCountString(l3);
            String string3 = ProtoOutputStream.getFieldTypeString(l4);
            if (string3 != null && string2 != null) {
                StringBuilder stringBuilder = new StringBuilder();
                if (l2 == 0xB00000000L) {
                    stringBuilder.append("start");
                } else {
                    stringBuilder.append("write");
                }
                stringBuilder.append(ProtoOutputStream.getFieldCountString(l5));
                stringBuilder.append(ProtoOutputStream.getFieldTypeString(l2));
                stringBuilder.append(" called for field ");
                stringBuilder.append((int)l);
                stringBuilder.append(" which should be used with ");
                if (l4 == 0xB00000000L) {
                    stringBuilder.append("start");
                } else {
                    stringBuilder.append("write");
                }
                stringBuilder.append(string2);
                stringBuilder.append(string3);
                if (l3 == 0x50000000000L) {
                    stringBuilder.append(" or writeRepeated");
                    stringBuilder.append(string3);
                }
                stringBuilder.append('.');
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (l2 == 0xB00000000L) {
                stringBuilder.append("start");
            } else {
                stringBuilder.append("write");
            }
            stringBuilder.append(ProtoOutputStream.getFieldCountString(l5));
            stringBuilder.append(ProtoOutputStream.getFieldTypeString(l2));
            stringBuilder.append(" called with an invalid fieldId: 0x");
            stringBuilder.append(Long.toHexString(l));
            stringBuilder.append(". The proto field ID might be ");
            stringBuilder.append((int)l);
            stringBuilder.append('.');
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid proto field ");
        stringBuilder.append((int)l);
        stringBuilder.append(" fieldId=");
        stringBuilder.append(Long.toHexString(l));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void compactIfNecessary() {
        if (!this.mCompacted) {
            if (this.mDepth == 0) {
                this.mBuffer.startEditing();
                int n = this.mBuffer.getReadableSize();
                this.editEncodedSize(n);
                this.mBuffer.rewindRead();
                this.compactSizes(n);
                int n2 = this.mCopyBegin;
                if (n2 < n) {
                    this.mBuffer.writeFromThisBuffer(n2, n - n2);
                }
                this.mBuffer.startEditing();
                this.mCompacted = true;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Trying to compact with ");
                stringBuilder.append(this.mDepth);
                stringBuilder.append(" missing calls to endObject");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
    }

    private void compactSizes(int n) {
        int n2;
        int n3 = this.mBuffer.getReadPos();
        while ((n2 = this.mBuffer.getReadPos()) < n3 + n) {
            int n4 = this.readRawTag();
            int n5 = n4 & 7;
            if (n5 != 0) {
                if (n5 != 1) {
                    Object object;
                    if (n5 != 2) {
                        if (n5 != 3 && n5 != 4) {
                            if (n5 == 5) {
                                this.mBuffer.skipRead(4);
                                continue;
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("compactSizes Bad tag tag=0x");
                            ((StringBuilder)object).append(Integer.toHexString(n4));
                            ((StringBuilder)object).append(" wireType=");
                            ((StringBuilder)object).append(n5);
                            ((StringBuilder)object).append(" -- ");
                            ((StringBuilder)object).append(this.mBuffer.getDebugString());
                            throw new ProtoParseException(((StringBuilder)object).toString());
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("groups not supported at index ");
                        ((StringBuilder)object).append(n2);
                        throw new RuntimeException(((StringBuilder)object).toString());
                    }
                    object = this.mBuffer;
                    ((EncodedBuffer)object).writeFromThisBuffer(this.mCopyBegin, ((EncodedBuffer)object).getReadPos() - this.mCopyBegin);
                    n5 = this.mBuffer.readRawFixed32();
                    n4 = this.mBuffer.readRawFixed32();
                    this.mBuffer.writeRawVarint32(n4);
                    this.mCopyBegin = this.mBuffer.getReadPos();
                    if (n5 >= 0) {
                        this.mBuffer.skipRead(n4);
                        continue;
                    }
                    this.compactSizes(-n5);
                    continue;
                }
                this.mBuffer.skipRead(8);
                continue;
            }
            while ((this.mBuffer.readRawByte() & 128) != 0) {
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int editEncodedSize(int var1_1) {
        var2_2 = this.mBuffer.getReadPos();
        var3_3 = 0;
        block0 : do {
            if ((var4_4 = this.mBuffer.getReadPos()) >= var2_2 + var1_1) return var3_3;
            var5_5 = this.readRawTag();
            var6_6 = var3_3 + EncodedBuffer.getRawVarint32Size(var5_5);
            var3_3 = var5_5 & 7;
            if (var3_3 != 0) {
                if (var3_3 != 1) {
                    if (var3_3 != 2) {
                        if (var3_3 != 3 && var3_3 != 4) {
                            if (var3_3 != 5) {
                                var7_7 = new StringBuilder();
                                var7_7.append("editEncodedSize Bad tag tag=0x");
                                var7_7.append(Integer.toHexString(var5_5));
                                var7_7.append(" wireType=");
                                var7_7.append(var3_3);
                                var7_7.append(" -- ");
                                var7_7.append(this.mBuffer.getDebugString());
                                throw new ProtoParseException(var7_7.toString());
                            }
                            var3_3 = var6_6 + 4;
                            this.mBuffer.skipRead(4);
                            continue;
                        }
                        var7_8 = new StringBuilder();
                        var7_8.append("groups not supported at index ");
                        var7_8.append(var4_4);
                        throw new RuntimeException(var7_8.toString());
                    }
                    var5_5 = this.mBuffer.readRawFixed32();
                    var4_4 = this.mBuffer.getReadPos();
                    var3_3 = this.mBuffer.readRawFixed32();
                    if (var5_5 >= 0) {
                        if (var3_3 != var5_5) {
                            var7_9 = new StringBuilder();
                            var7_9.append("Pre-computed size where the precomputed size and the raw size in the buffer don't match! childRawSize=");
                            var7_9.append(var5_5);
                            var7_9.append(" childEncodedSize=");
                            var7_9.append(var3_3);
                            var7_9.append(" childEncodedSizePos=");
                            var7_9.append(var4_4);
                            throw new RuntimeException(var7_9.toString());
                        }
                        this.mBuffer.skipRead(var5_5);
                    } else {
                        var3_3 = this.editEncodedSize(-var5_5);
                        this.mBuffer.editRawFixed32(var4_4, var3_3);
                    }
                    var3_3 = var6_6 + (EncodedBuffer.getRawVarint32Size(var3_3) + var3_3);
                    continue;
                }
                var3_3 = var6_6 + 8;
                this.mBuffer.skipRead(8);
                continue;
            }
            ++var6_6;
            do {
                var3_3 = var6_6++;
                if ((this.mBuffer.readRawByte() & 128) != 0) ** break;
                continue block0;
            } while (true);
            break;
        } while (true);
    }

    private void endObjectImpl(long l, boolean bl) {
        int n = ProtoOutputStream.getDepthFromToken(l);
        boolean bl2 = ProtoOutputStream.getRepeatedFromToken(l);
        int n2 = ProtoOutputStream.getOffsetFromToken(l);
        int n3 = this.mBuffer.getWritePos() - n2 - 8;
        if (bl != bl2) {
            if (bl) {
                throw new IllegalArgumentException("endRepeatedObject called where endObject should have been");
            }
            throw new IllegalArgumentException("endObject called where endRepeatedObject should have been");
        }
        if ((this.mDepth & 511) == n && this.mExpectedObjectToken == l) {
            this.mExpectedObjectToken = (long)this.mBuffer.getRawFixed32At(n2) << 32 | 0xFFFFFFFFL & (long)this.mBuffer.getRawFixed32At(n2 + 4);
            --this.mDepth;
            if (n3 > 0) {
                this.mBuffer.editRawFixed32(n2, -n3);
                this.mBuffer.editRawFixed32(n2 + 4, -1);
            } else if (bl) {
                this.mBuffer.editRawFixed32(n2, 0);
                this.mBuffer.editRawFixed32(n2 + 4, 0);
            } else {
                this.mBuffer.rewindWriteTo(n2 - ProtoOutputStream.getTagSizeFromToken(l));
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Mismatched startObject/endObject calls. Current depth ");
        stringBuilder.append(this.mDepth);
        stringBuilder.append(" token=");
        stringBuilder.append(ProtoOutputStream.token2String(l));
        stringBuilder.append(" expectedToken=");
        stringBuilder.append(ProtoOutputStream.token2String(this.mExpectedObjectToken));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static int getTagSize(int n) {
        return EncodedBuffer.getRawVarint32Size(n << 3);
    }

    public static long makeFieldId(int n, long l) {
        return (long)n & 0xFFFFFFFFL | l;
    }

    private int readRawTag() {
        if (this.mBuffer.getReadPos() == this.mBuffer.getReadableSize()) {
            return 0;
        }
        return (int)this.mBuffer.readRawUnsigned();
    }

    private long startObjectImpl(int n, boolean bl) {
        this.writeTag(n, 2);
        int n2 = this.mBuffer.getWritePos();
        ++this.mDepth;
        --this.mNextObjectId;
        this.mBuffer.writeRawFixed32((int)(this.mExpectedObjectToken >> 32));
        this.mBuffer.writeRawFixed32((int)this.mExpectedObjectToken);
        long l = this.mExpectedObjectToken;
        this.mExpectedObjectToken = ProtoOutputStream.makeToken(ProtoOutputStream.getTagSize(n), bl, this.mDepth, this.mNextObjectId, n2);
        return this.mExpectedObjectToken;
    }

    private void writeBoolImpl(int n, boolean bl) {
        if (bl) {
            this.writeTag(n, 0);
            this.mBuffer.writeRawByte((byte)1);
        }
    }

    private void writeBytesImpl(int n, byte[] arrby) {
        if (arrby != null && arrby.length > 0) {
            this.writeKnownLengthHeader(n, arrby.length);
            this.mBuffer.writeRawBuffer(arrby);
        }
    }

    private void writeDoubleImpl(int n, double d) {
        if (d != 0.0) {
            this.writeTag(n, 1);
            this.mBuffer.writeRawFixed64(Double.doubleToLongBits(d));
        }
    }

    private void writeEnumImpl(int n, int n2) {
        if (n2 != 0) {
            this.writeTag(n, 0);
            this.writeUnsignedVarintFromSignedInt(n2);
        }
    }

    private void writeFixed32Impl(int n, int n2) {
        if (n2 != 0) {
            this.writeTag(n, 5);
            this.mBuffer.writeRawFixed32(n2);
        }
    }

    private void writeFixed64Impl(int n, long l) {
        if (l != 0L) {
            this.writeTag(n, 1);
            this.mBuffer.writeRawFixed64(l);
        }
    }

    private void writeFloatImpl(int n, float f) {
        if (f != 0.0f) {
            this.writeTag(n, 5);
            this.mBuffer.writeRawFixed32(Float.floatToIntBits(f));
        }
    }

    private void writeInt32Impl(int n, int n2) {
        if (n2 != 0) {
            this.writeTag(n, 0);
            this.writeUnsignedVarintFromSignedInt(n2);
        }
    }

    private void writeInt64Impl(int n, long l) {
        if (l != 0L) {
            this.writeTag(n, 0);
            this.mBuffer.writeRawVarint64(l);
        }
    }

    private void writeKnownLengthHeader(int n, int n2) {
        this.writeTag(n, 2);
        this.mBuffer.writeRawFixed32(n2);
        this.mBuffer.writeRawFixed32(n2);
    }

    private void writeRepeatedBoolImpl(int n, boolean bl) {
        this.writeTag(n, 0);
        this.mBuffer.writeRawByte((byte)(bl ? 1 : 0));
    }

    private void writeRepeatedBytesImpl(int n, byte[] arrby) {
        int n2 = arrby == null ? 0 : arrby.length;
        this.writeKnownLengthHeader(n, n2);
        this.mBuffer.writeRawBuffer(arrby);
    }

    private void writeRepeatedDoubleImpl(int n, double d) {
        this.writeTag(n, 1);
        this.mBuffer.writeRawFixed64(Double.doubleToLongBits(d));
    }

    private void writeRepeatedEnumImpl(int n, int n2) {
        this.writeTag(n, 0);
        this.writeUnsignedVarintFromSignedInt(n2);
    }

    private void writeRepeatedFixed32Impl(int n, int n2) {
        this.writeTag(n, 5);
        this.mBuffer.writeRawFixed32(n2);
    }

    private void writeRepeatedFixed64Impl(int n, long l) {
        this.writeTag(n, 1);
        this.mBuffer.writeRawFixed64(l);
    }

    private void writeRepeatedFloatImpl(int n, float f) {
        this.writeTag(n, 5);
        this.mBuffer.writeRawFixed32(Float.floatToIntBits(f));
    }

    private void writeRepeatedInt32Impl(int n, int n2) {
        this.writeTag(n, 0);
        this.writeUnsignedVarintFromSignedInt(n2);
    }

    private void writeRepeatedInt64Impl(int n, long l) {
        this.writeTag(n, 0);
        this.mBuffer.writeRawVarint64(l);
    }

    private void writeRepeatedSFixed32Impl(int n, int n2) {
        this.writeTag(n, 5);
        this.mBuffer.writeRawFixed32(n2);
    }

    private void writeRepeatedSFixed64Impl(int n, long l) {
        this.writeTag(n, 1);
        this.mBuffer.writeRawFixed64(l);
    }

    private void writeRepeatedSInt32Impl(int n, int n2) {
        this.writeTag(n, 0);
        this.mBuffer.writeRawZigZag32(n2);
    }

    private void writeRepeatedSInt64Impl(int n, long l) {
        this.writeTag(n, 0);
        this.mBuffer.writeRawZigZag64(l);
    }

    private void writeRepeatedStringImpl(int n, String string2) {
        if (string2 != null && string2.length() != 0) {
            this.writeUtf8String(n, string2);
        } else {
            this.writeKnownLengthHeader(n, 0);
        }
    }

    private void writeRepeatedUInt32Impl(int n, int n2) {
        this.writeTag(n, 0);
        this.mBuffer.writeRawVarint32(n2);
    }

    private void writeRepeatedUInt64Impl(int n, long l) {
        this.writeTag(n, 0);
        this.mBuffer.writeRawVarint64(l);
    }

    private void writeSFixed32Impl(int n, int n2) {
        if (n2 != 0) {
            this.writeTag(n, 5);
            this.mBuffer.writeRawFixed32(n2);
        }
    }

    private void writeSFixed64Impl(int n, long l) {
        if (l != 0L) {
            this.writeTag(n, 1);
            this.mBuffer.writeRawFixed64(l);
        }
    }

    private void writeSInt32Impl(int n, int n2) {
        if (n2 != 0) {
            this.writeTag(n, 0);
            this.mBuffer.writeRawZigZag32(n2);
        }
    }

    private void writeSInt64Impl(int n, long l) {
        if (l != 0L) {
            this.writeTag(n, 0);
            this.mBuffer.writeRawZigZag64(l);
        }
    }

    private void writeStringImpl(int n, String string2) {
        if (string2 != null && string2.length() > 0) {
            this.writeUtf8String(n, string2);
        }
    }

    private void writeUInt32Impl(int n, int n2) {
        if (n2 != 0) {
            this.writeTag(n, 0);
            this.mBuffer.writeRawVarint32(n2);
        }
    }

    private void writeUInt64Impl(int n, long l) {
        if (l != 0L) {
            this.writeTag(n, 0);
            this.mBuffer.writeRawVarint64(l);
        }
    }

    private void writeUnsignedVarintFromSignedInt(int n) {
        if (n >= 0) {
            this.mBuffer.writeRawVarint32(n);
        } else {
            this.mBuffer.writeRawVarint64(n);
        }
    }

    private void writeUtf8String(int n, String arrby) {
        try {
            arrby = arrby.getBytes("UTF-8");
            this.writeKnownLengthHeader(n, arrby.length);
            this.mBuffer.writeRawBuffer(arrby);
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("not possible");
        }
    }

    public void dump(String string2) {
        Log.d(string2, this.mBuffer.getDebugString());
        this.mBuffer.dumpBuffers(string2);
    }

    public void end(long l) {
        this.endObjectImpl(l, ProtoOutputStream.getRepeatedFromToken(l));
    }

    @Deprecated
    public void endObject(long l) {
        this.assertNotCompacted();
        this.endObjectImpl(l, false);
    }

    @Deprecated
    public void endRepeatedObject(long l) {
        this.assertNotCompacted();
        this.endObjectImpl(l, true);
    }

    public void flush() {
        if (this.mStream == null) {
            return;
        }
        if (this.mDepth != 0) {
            return;
        }
        if (this.mCompacted) {
            return;
        }
        this.compactIfNecessary();
        byte[] arrby = this.mBuffer;
        arrby = arrby.getBytes(arrby.getReadableSize());
        try {
            this.mStream.write(arrby);
            this.mStream.flush();
            return;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Error flushing proto to stream", iOException);
        }
    }

    public byte[] getBytes() {
        this.compactIfNecessary();
        EncodedBuffer encodedBuffer = this.mBuffer;
        return encodedBuffer.getBytes(encodedBuffer.getReadableSize());
    }

    public int getRawSize() {
        if (this.mCompacted) {
            return this.getBytes().length;
        }
        return this.mBuffer.getSize();
    }

    public long start(long l) {
        this.assertNotCompacted();
        int n = (int)l;
        if ((0xFF00000000L & l) == 0xB00000000L) {
            long l2 = 0xF0000000000L & l;
            if (l2 == 0x10000000000L) {
                return this.startObjectImpl(n, false);
            }
            if (l2 == 0x20000000000L || l2 == 0x50000000000L) {
                return this.startObjectImpl(n, true);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempt to call start(long) with ");
        stringBuilder.append(ProtoOutputStream.getFieldIdString(l));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Deprecated
    public long startObject(long l) {
        this.assertNotCompacted();
        return this.startObjectImpl(ProtoOutputStream.checkFieldId(l, 1146756268032L), false);
    }

    @Deprecated
    public long startRepeatedObject(long l) {
        this.assertNotCompacted();
        return this.startObjectImpl(ProtoOutputStream.checkFieldId(l, 2246267895808L), true);
    }

    /*
     * Exception decompiling
     */
    public void write(long var1_1, double var3_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public void write(long var1_1, float var3_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public void write(long var1_1, int var3_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public void write(long var1_1, long var3_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public void write(long l, String charSequence) {
        this.assertNotCompacted();
        int n = (int)l;
        int n2 = (int)((0xFFF00000000L & l) >> 32);
        if (n2 != 265) {
            if (n2 != 521 && n2 != 1289) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Attempt to call write(long, String) with ");
                ((StringBuilder)charSequence).append(ProtoOutputStream.getFieldIdString(l));
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            this.writeRepeatedStringImpl(n, (String)charSequence);
        } else {
            this.writeStringImpl(n, (String)charSequence);
        }
    }

    public void write(long l, boolean bl) {
        this.assertNotCompacted();
        int n = (int)l;
        int n2 = (int)((0xFFF00000000L & l) >> 32);
        if (n2 != 264) {
            if (n2 != 520 && n2 != 1288) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Attempt to call write(long, boolean) with ");
                stringBuilder.append(ProtoOutputStream.getFieldIdString(l));
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.writeRepeatedBoolImpl(n, bl);
        } else {
            this.writeBoolImpl(n, bl);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void write(long var1_1, byte[] var3_2) {
        this.assertNotCompacted();
        var4_3 = (int)var1_1;
        var5_4 = (int)((0xFFF00000000L & var1_1) >> 32);
        if (var5_4 == 267) {
            this.writeObjectImpl(var4_3, (byte[])var3_2);
            return;
        }
        if (var5_4 == 268) {
            this.writeBytesImpl(var4_3, (byte[])var3_2);
            return;
        }
        if (var5_4 != 523) {
            if (var5_4 != 524) {
                if (var5_4 != 1291) {
                    if (var5_4 != 1292) {
                        var3_2 = new StringBuilder();
                        var3_2.append("Attempt to call write(long, byte[]) with ");
                        var3_2.append(ProtoOutputStream.getFieldIdString(var1_1));
                        throw new IllegalArgumentException(var3_2.toString());
                    } else {
                        ** GOTO lbl-1000
                    }
                }
            } else lbl-1000: // 3 sources:
            {
                this.writeRepeatedBytesImpl(var4_3, (byte[])var3_2);
                return;
            }
        }
        this.writeRepeatedObjectImpl(var4_3, (byte[])var3_2);
    }

    @Deprecated
    public void writeBool(long l, boolean bl) {
        this.assertNotCompacted();
        this.writeBoolImpl(ProtoOutputStream.checkFieldId(l, 1133871366144L), bl);
    }

    @Deprecated
    public void writeBytes(long l, byte[] arrby) {
        this.assertNotCompacted();
        this.writeBytesImpl(ProtoOutputStream.checkFieldId(l, 1151051235328L), arrby);
    }

    @Deprecated
    public void writeDouble(long l, double d) {
        this.assertNotCompacted();
        this.writeDoubleImpl(ProtoOutputStream.checkFieldId(l, 0x10100000000L), d);
    }

    @Deprecated
    public void writeEnum(long l, int n) {
        this.assertNotCompacted();
        this.writeEnumImpl(ProtoOutputStream.checkFieldId(l, 1159641169920L), n);
    }

    @Deprecated
    public void writeFixed32(long l, int n) {
        this.assertNotCompacted();
        this.writeFixed32Impl(ProtoOutputStream.checkFieldId(l, 1129576398848L), n);
    }

    @Deprecated
    public void writeFixed64(long l, long l2) {
        this.assertNotCompacted();
        this.writeFixed64Impl(ProtoOutputStream.checkFieldId(l, 1125281431552L), l2);
    }

    @Deprecated
    public void writeFloat(long l, float f) {
        this.assertNotCompacted();
        this.writeFloatImpl(ProtoOutputStream.checkFieldId(l, 1108101562368L), f);
    }

    @Deprecated
    public void writeInt32(long l, int n) {
        this.assertNotCompacted();
        this.writeInt32Impl(ProtoOutputStream.checkFieldId(l, 1120986464256L), n);
    }

    @Deprecated
    public void writeInt64(long l, long l2) {
        this.assertNotCompacted();
        this.writeInt64Impl(ProtoOutputStream.checkFieldId(l, 1112396529664L), l2);
    }

    @Deprecated
    public void writeObject(long l, byte[] arrby) {
        this.assertNotCompacted();
        this.writeObjectImpl(ProtoOutputStream.checkFieldId(l, 1146756268032L), arrby);
    }

    void writeObjectImpl(int n, byte[] arrby) {
        if (arrby != null && arrby.length != 0) {
            this.writeKnownLengthHeader(n, arrby.length);
            this.mBuffer.writeRawBuffer(arrby);
        }
    }

    @Deprecated
    public void writePackedBool(long l, boolean[] arrbl) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5531917877248L);
        int n2 = arrbl != null ? arrbl.length : 0;
        if (n2 > 0) {
            this.writeKnownLengthHeader(n, n2);
            for (n = 0; n < n2; ++n) {
                this.mBuffer.writeRawByte((byte)(arrbl[n] ? 1 : 0));
            }
        }
    }

    @Deprecated
    public void writePackedDouble(long l, double[] arrd) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5501853106176L);
        int n2 = arrd != null ? arrd.length : 0;
        if (n2 > 0) {
            this.writeKnownLengthHeader(n, n2 * 8);
            for (n = 0; n < n2; ++n) {
                this.mBuffer.writeRawFixed64(Double.doubleToLongBits(arrd[n]));
            }
        }
    }

    @Deprecated
    public void writePackedEnum(long l, int[] arrn) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5557687681024L);
        int n2 = arrn != null ? arrn.length : 0;
        if (n2 > 0) {
            int n3;
            int n4 = 0;
            for (n3 = 0; n3 < n2; ++n3) {
                int n5 = arrn[n3];
                n5 = n5 >= 0 ? EncodedBuffer.getRawVarint32Size(n5) : 10;
                n4 += n5;
            }
            this.writeKnownLengthHeader(n, n4);
            for (n3 = 0; n3 < n2; ++n3) {
                this.writeUnsignedVarintFromSignedInt(arrn[n3]);
            }
        }
    }

    @Deprecated
    public void writePackedFixed32(long l, int[] arrn) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5527622909952L);
        int n2 = arrn != null ? arrn.length : 0;
        if (n2 > 0) {
            this.writeKnownLengthHeader(n, n2 * 4);
            for (n = 0; n < n2; ++n) {
                this.mBuffer.writeRawFixed32(arrn[n]);
            }
        }
    }

    @Deprecated
    public void writePackedFixed64(long l, long[] arrl) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5523327942656L);
        int n2 = arrl != null ? arrl.length : 0;
        if (n2 > 0) {
            this.writeKnownLengthHeader(n, n2 * 8);
            for (n = 0; n < n2; ++n) {
                this.mBuffer.writeRawFixed64(arrl[n]);
            }
        }
    }

    @Deprecated
    public void writePackedFloat(long l, float[] arrf) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5506148073472L);
        int n2 = arrf != null ? arrf.length : 0;
        if (n2 > 0) {
            this.writeKnownLengthHeader(n, n2 * 4);
            for (n = 0; n < n2; ++n) {
                this.mBuffer.writeRawFixed32(Float.floatToIntBits(arrf[n]));
            }
        }
    }

    @Deprecated
    public void writePackedInt32(long l, int[] arrn) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 0x50500000000L);
        int n2 = arrn != null ? arrn.length : 0;
        if (n2 > 0) {
            int n3;
            int n4 = 0;
            for (n3 = 0; n3 < n2; ++n3) {
                int n5 = arrn[n3];
                n5 = n5 >= 0 ? EncodedBuffer.getRawVarint32Size(n5) : 10;
                n4 += n5;
            }
            this.writeKnownLengthHeader(n, n4);
            for (n3 = 0; n3 < n2; ++n3) {
                this.writeUnsignedVarintFromSignedInt(arrn[n3]);
            }
        }
    }

    @Deprecated
    public void writePackedInt64(long l, long[] arrl) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5510443040768L);
        int n2 = arrl != null ? arrl.length : 0;
        if (n2 > 0) {
            int n3;
            int n4 = 0;
            for (n3 = 0; n3 < n2; ++n3) {
                n4 += EncodedBuffer.getRawVarint64Size(arrl[n3]);
            }
            this.writeKnownLengthHeader(n, n4);
            for (n3 = 0; n3 < n2; ++n3) {
                this.mBuffer.writeRawVarint64(arrl[n3]);
            }
        }
    }

    @Deprecated
    public void writePackedSFixed32(long l, int[] arrn) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5561982648320L);
        int n2 = arrn != null ? arrn.length : 0;
        if (n2 > 0) {
            this.writeKnownLengthHeader(n, n2 * 4);
            for (n = 0; n < n2; ++n) {
                this.mBuffer.writeRawFixed32(arrn[n]);
            }
        }
    }

    @Deprecated
    public void writePackedSFixed64(long l, long[] arrl) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5566277615616L);
        int n2 = arrl != null ? arrl.length : 0;
        if (n2 > 0) {
            this.writeKnownLengthHeader(n, n2 * 8);
            for (n = 0; n < n2; ++n) {
                this.mBuffer.writeRawFixed64(arrl[n]);
            }
        }
    }

    @Deprecated
    public void writePackedSInt32(long l, int[] arrn) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5570572582912L);
        int n2 = arrn != null ? arrn.length : 0;
        if (n2 > 0) {
            int n3;
            int n4 = 0;
            for (n3 = 0; n3 < n2; ++n3) {
                n4 += EncodedBuffer.getRawZigZag32Size(arrn[n3]);
            }
            this.writeKnownLengthHeader(n, n4);
            for (n3 = 0; n3 < n2; ++n3) {
                this.mBuffer.writeRawZigZag32(arrn[n3]);
            }
        }
    }

    @Deprecated
    public void writePackedSInt64(long l, long[] arrl) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5574867550208L);
        int n2 = arrl != null ? arrl.length : 0;
        if (n2 > 0) {
            int n3;
            int n4 = 0;
            for (n3 = 0; n3 < n2; ++n3) {
                n4 += EncodedBuffer.getRawZigZag64Size(arrl[n3]);
            }
            this.writeKnownLengthHeader(n, n4);
            for (n3 = 0; n3 < n2; ++n3) {
                this.mBuffer.writeRawZigZag64(arrl[n3]);
            }
        }
    }

    @Deprecated
    public void writePackedUInt32(long l, int[] arrn) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5553392713728L);
        int n2 = arrn != null ? arrn.length : 0;
        if (n2 > 0) {
            int n3;
            int n4 = 0;
            for (n3 = 0; n3 < n2; ++n3) {
                n4 += EncodedBuffer.getRawVarint32Size(arrn[n3]);
            }
            this.writeKnownLengthHeader(n, n4);
            for (n3 = 0; n3 < n2; ++n3) {
                this.mBuffer.writeRawVarint32(arrn[n3]);
            }
        }
    }

    @Deprecated
    public void writePackedUInt64(long l, long[] arrl) {
        this.assertNotCompacted();
        int n = ProtoOutputStream.checkFieldId(l, 5514738008064L);
        int n2 = arrl != null ? arrl.length : 0;
        if (n2 > 0) {
            int n3;
            int n4 = 0;
            for (n3 = 0; n3 < n2; ++n3) {
                n4 += EncodedBuffer.getRawVarint64Size(arrl[n3]);
            }
            this.writeKnownLengthHeader(n, n4);
            for (n3 = 0; n3 < n2; ++n3) {
                this.mBuffer.writeRawVarint64(arrl[n3]);
            }
        }
    }

    @Deprecated
    public void writeRepeatedBool(long l, boolean bl) {
        this.assertNotCompacted();
        this.writeRepeatedBoolImpl(ProtoOutputStream.checkFieldId(l, 2233382993920L), bl);
    }

    @Deprecated
    public void writeRepeatedBytes(long l, byte[] arrby) {
        this.assertNotCompacted();
        this.writeRepeatedBytesImpl(ProtoOutputStream.checkFieldId(l, 2250562863104L), arrby);
    }

    @Deprecated
    public void writeRepeatedDouble(long l, double d) {
        this.assertNotCompacted();
        this.writeRepeatedDoubleImpl(ProtoOutputStream.checkFieldId(l, 2203318222848L), d);
    }

    @Deprecated
    public void writeRepeatedEnum(long l, int n) {
        this.assertNotCompacted();
        this.writeRepeatedEnumImpl(ProtoOutputStream.checkFieldId(l, 2259152797696L), n);
    }

    @Deprecated
    public void writeRepeatedFixed32(long l, int n) {
        this.assertNotCompacted();
        this.writeRepeatedFixed32Impl(ProtoOutputStream.checkFieldId(l, 2229088026624L), n);
    }

    @Deprecated
    public void writeRepeatedFixed64(long l, long l2) {
        this.assertNotCompacted();
        this.writeRepeatedFixed64Impl(ProtoOutputStream.checkFieldId(l, 2224793059328L), l2);
    }

    @Deprecated
    public void writeRepeatedFloat(long l, float f) {
        this.assertNotCompacted();
        this.writeRepeatedFloatImpl(ProtoOutputStream.checkFieldId(l, 0x20200000000L), f);
    }

    @Deprecated
    public void writeRepeatedInt32(long l, int n) {
        this.assertNotCompacted();
        this.writeRepeatedInt32Impl(ProtoOutputStream.checkFieldId(l, 2220498092032L), n);
    }

    @Deprecated
    public void writeRepeatedInt64(long l, long l2) {
        this.assertNotCompacted();
        this.writeRepeatedInt64Impl(ProtoOutputStream.checkFieldId(l, 2211908157440L), l2);
    }

    @Deprecated
    public void writeRepeatedObject(long l, byte[] arrby) {
        this.assertNotCompacted();
        this.writeRepeatedObjectImpl(ProtoOutputStream.checkFieldId(l, 2246267895808L), arrby);
    }

    void writeRepeatedObjectImpl(int n, byte[] arrby) {
        int n2 = arrby == null ? 0 : arrby.length;
        this.writeKnownLengthHeader(n, n2);
        this.mBuffer.writeRawBuffer(arrby);
    }

    @Deprecated
    public void writeRepeatedSFixed32(long l, int n) {
        this.assertNotCompacted();
        this.writeRepeatedSFixed32Impl(ProtoOutputStream.checkFieldId(l, 2263447764992L), n);
    }

    @Deprecated
    public void writeRepeatedSFixed64(long l, long l2) {
        this.assertNotCompacted();
        this.writeRepeatedSFixed64Impl(ProtoOutputStream.checkFieldId(l, 2267742732288L), l2);
    }

    @Deprecated
    public void writeRepeatedSInt32(long l, int n) {
        this.assertNotCompacted();
        this.writeRepeatedSInt32Impl(ProtoOutputStream.checkFieldId(l, 2272037699584L), n);
    }

    @Deprecated
    public void writeRepeatedSInt64(long l, long l2) {
        this.assertNotCompacted();
        this.writeRepeatedSInt64Impl(ProtoOutputStream.checkFieldId(l, 2276332666880L), l2);
    }

    @Deprecated
    public void writeRepeatedString(long l, String string2) {
        this.assertNotCompacted();
        this.writeRepeatedStringImpl(ProtoOutputStream.checkFieldId(l, 2237677961216L), string2);
    }

    @Deprecated
    public void writeRepeatedUInt32(long l, int n) {
        this.assertNotCompacted();
        this.writeRepeatedUInt32Impl(ProtoOutputStream.checkFieldId(l, 2254857830400L), n);
    }

    @Deprecated
    public void writeRepeatedUInt64(long l, long l2) {
        this.assertNotCompacted();
        this.writeRepeatedUInt64Impl(ProtoOutputStream.checkFieldId(l, 2216203124736L), l2);
    }

    @Deprecated
    public void writeSFixed32(long l, int n) {
        this.assertNotCompacted();
        this.writeSFixed32Impl(ProtoOutputStream.checkFieldId(l, 1163936137216L), n);
    }

    @Deprecated
    public void writeSFixed64(long l, long l2) {
        this.assertNotCompacted();
        this.writeSFixed64Impl(ProtoOutputStream.checkFieldId(l, 0x11000000000L), l2);
    }

    @Deprecated
    public void writeSInt32(long l, int n) {
        this.assertNotCompacted();
        this.writeSInt32Impl(ProtoOutputStream.checkFieldId(l, 0x11100000000L), n);
    }

    @Deprecated
    public void writeSInt64(long l, long l2) {
        this.assertNotCompacted();
        this.writeSInt64Impl(ProtoOutputStream.checkFieldId(l, 1176821039104L), l2);
    }

    @Deprecated
    public void writeString(long l, String string2) {
        this.assertNotCompacted();
        this.writeStringImpl(ProtoOutputStream.checkFieldId(l, 1138166333440L), string2);
    }

    public void writeTag(int n, int n2) {
        this.mBuffer.writeRawVarint32(n << 3 | n2);
    }

    @Deprecated
    public void writeUInt32(long l, int n) {
        this.assertNotCompacted();
        this.writeUInt32Impl(ProtoOutputStream.checkFieldId(l, 1155346202624L), n);
    }

    @Deprecated
    public void writeUInt64(long l, long l2) {
        this.assertNotCompacted();
        this.writeUInt64Impl(ProtoOutputStream.checkFieldId(l, 1116691496960L), l2);
    }
}

