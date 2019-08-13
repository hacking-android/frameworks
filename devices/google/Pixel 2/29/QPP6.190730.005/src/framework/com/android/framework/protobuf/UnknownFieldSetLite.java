/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.CodedOutputStream;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.framework.protobuf.MessageLiteToString;
import com.android.framework.protobuf.WireFormat;
import java.io.IOException;
import java.util.Arrays;

public final class UnknownFieldSetLite {
    private static final UnknownFieldSetLite DEFAULT_INSTANCE = new UnknownFieldSetLite(0, new int[0], new Object[0], false);
    private static final int MIN_CAPACITY = 8;
    private int count;
    private boolean isMutable;
    private int memoizedSerializedSize = -1;
    private Object[] objects;
    private int[] tags;

    private UnknownFieldSetLite() {
        this(0, new int[8], new Object[8], true);
    }

    private UnknownFieldSetLite(int n, int[] arrn, Object[] arrobject, boolean bl) {
        this.count = n;
        this.tags = arrn;
        this.objects = arrobject;
        this.isMutable = bl;
    }

    private void ensureCapacity() {
        int n = this.count;
        if (n == this.tags.length) {
            n = n < 4 ? 8 : (n >>= 1);
            n = this.count + n;
            this.tags = Arrays.copyOf(this.tags, n);
            this.objects = Arrays.copyOf(this.objects, n);
        }
    }

    public static UnknownFieldSetLite getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private UnknownFieldSetLite mergeFrom(CodedInputStream codedInputStream) throws IOException {
        int n;
        while ((n = codedInputStream.readTag()) != 0 && this.mergeFieldFrom(n, codedInputStream)) {
        }
        return this;
    }

    static UnknownFieldSetLite mutableCopyOf(UnknownFieldSetLite unknownFieldSetLite, UnknownFieldSetLite unknownFieldSetLite2) {
        int n = unknownFieldSetLite.count + unknownFieldSetLite2.count;
        int[] arrn = Arrays.copyOf(unknownFieldSetLite.tags, n);
        System.arraycopy(unknownFieldSetLite2.tags, 0, arrn, unknownFieldSetLite.count, unknownFieldSetLite2.count);
        Object[] arrobject = Arrays.copyOf(unknownFieldSetLite.objects, n);
        System.arraycopy(unknownFieldSetLite2.objects, 0, arrobject, unknownFieldSetLite.count, unknownFieldSetLite2.count);
        return new UnknownFieldSetLite(n, arrn, arrobject, true);
    }

    static UnknownFieldSetLite newInstance() {
        return new UnknownFieldSetLite();
    }

    private void storeField(int n, Object object) {
        this.ensureCapacity();
        int[] arrn = this.tags;
        int n2 = this.count;
        arrn[n2] = n;
        this.objects[n2] = object;
        this.count = n2 + 1;
    }

    void checkMutable() {
        if (this.isMutable) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof UnknownFieldSetLite)) {
            return false;
        }
        object = (UnknownFieldSetLite)object;
        return this.count == ((UnknownFieldSetLite)object).count && Arrays.equals(this.tags, ((UnknownFieldSetLite)object).tags) && Arrays.deepEquals(this.objects, ((UnknownFieldSetLite)object).objects);
        {
        }
    }

    public int getSerializedSize() {
        int n = this.memoizedSerializedSize;
        if (n != -1) {
            return n;
        }
        n = 0;
        for (int i = 0; i < this.count; ++i) {
            int n2 = this.tags[i];
            int n3 = WireFormat.getTagFieldNumber(n2);
            if ((n2 = WireFormat.getTagWireType(n2)) != 0) {
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 != 3) {
                            if (n2 == 5) {
                                n += CodedOutputStream.computeFixed32Size(n3, (Integer)this.objects[i]);
                                continue;
                            }
                            throw new IllegalStateException(InvalidProtocolBufferException.invalidWireType());
                        }
                        n += CodedOutputStream.computeTagSize(n3) * 2 + ((UnknownFieldSetLite)this.objects[i]).getSerializedSize();
                        continue;
                    }
                    n += CodedOutputStream.computeBytesSize(n3, (ByteString)this.objects[i]);
                    continue;
                }
                n += CodedOutputStream.computeFixed64Size(n3, (Long)this.objects[i]);
                continue;
            }
            n += CodedOutputStream.computeUInt64Size(n3, (Long)this.objects[i]);
        }
        this.memoizedSerializedSize = n;
        return n;
    }

    public int hashCode() {
        return ((17 * 31 + this.count) * 31 + Arrays.hashCode(this.tags)) * 31 + Arrays.deepHashCode(this.objects);
    }

    public void makeImmutable() {
        this.isMutable = false;
    }

    boolean mergeFieldFrom(int n, CodedInputStream codedInputStream) throws IOException {
        this.checkMutable();
        int n2 = WireFormat.getTagFieldNumber(n);
        int n3 = WireFormat.getTagWireType(n);
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 4) {
                            if (n3 == 5) {
                                this.storeField(n, codedInputStream.readFixed32());
                                return true;
                            }
                            throw InvalidProtocolBufferException.invalidWireType();
                        }
                        return false;
                    }
                    UnknownFieldSetLite unknownFieldSetLite = new UnknownFieldSetLite();
                    unknownFieldSetLite.mergeFrom(codedInputStream);
                    codedInputStream.checkLastTagWas(WireFormat.makeTag(n2, 4));
                    this.storeField(n, unknownFieldSetLite);
                    return true;
                }
                this.storeField(n, codedInputStream.readBytes());
                return true;
            }
            this.storeField(n, codedInputStream.readFixed64());
            return true;
        }
        this.storeField(n, codedInputStream.readInt64());
        return true;
    }

    UnknownFieldSetLite mergeLengthDelimitedField(int n, ByteString byteString) {
        this.checkMutable();
        if (n != 0) {
            this.storeField(WireFormat.makeTag(n, 2), byteString);
            return this;
        }
        throw new IllegalArgumentException("Zero is not a valid field number.");
    }

    UnknownFieldSetLite mergeVarintField(int n, int n2) {
        this.checkMutable();
        if (n != 0) {
            this.storeField(WireFormat.makeTag(n, 0), n2);
            return this;
        }
        throw new IllegalArgumentException("Zero is not a valid field number.");
    }

    final void printWithIndent(StringBuilder stringBuilder, int n) {
        for (int i = 0; i < this.count; ++i) {
            MessageLiteToString.printField(stringBuilder, n, String.valueOf(WireFormat.getTagFieldNumber(this.tags[i])), this.objects[i]);
        }
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        for (int i = 0; i < this.count; ++i) {
            int n = this.tags[i];
            int n2 = WireFormat.getTagFieldNumber(n);
            if ((n = WireFormat.getTagWireType(n)) != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n == 5) {
                                codedOutputStream.writeFixed32(n2, (Integer)this.objects[i]);
                                continue;
                            }
                            throw InvalidProtocolBufferException.invalidWireType();
                        }
                        codedOutputStream.writeTag(n2, 3);
                        ((UnknownFieldSetLite)this.objects[i]).writeTo(codedOutputStream);
                        codedOutputStream.writeTag(n2, 4);
                        continue;
                    }
                    codedOutputStream.writeBytes(n2, (ByteString)this.objects[i]);
                    continue;
                }
                codedOutputStream.writeFixed64(n2, (Long)this.objects[i]);
                continue;
            }
            codedOutputStream.writeUInt64(n2, (Long)this.objects[i]);
        }
    }
}

