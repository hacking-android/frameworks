/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.protobuf.nano;

import com.android.internal.telephony.protobuf.nano.CodedInputByteBufferNano;
import com.android.internal.telephony.protobuf.nano.CodedOutputByteBufferNano;
import com.android.internal.telephony.protobuf.nano.ExtendableMessageNano;
import com.android.internal.telephony.protobuf.nano.MessageNano;
import com.android.internal.telephony.protobuf.nano.UnknownFieldData;
import com.android.internal.telephony.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Extension<M extends ExtendableMessageNano<M>, T> {
    public static final int TYPE_BOOL = 8;
    public static final int TYPE_BYTES = 12;
    public static final int TYPE_DOUBLE = 1;
    public static final int TYPE_ENUM = 14;
    public static final int TYPE_FIXED32 = 7;
    public static final int TYPE_FIXED64 = 6;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_GROUP = 10;
    public static final int TYPE_INT32 = 5;
    public static final int TYPE_INT64 = 3;
    public static final int TYPE_MESSAGE = 11;
    public static final int TYPE_SFIXED32 = 15;
    public static final int TYPE_SFIXED64 = 16;
    public static final int TYPE_SINT32 = 17;
    public static final int TYPE_SINT64 = 18;
    public static final int TYPE_STRING = 9;
    public static final int TYPE_UINT32 = 13;
    public static final int TYPE_UINT64 = 4;
    protected final Class<T> clazz;
    protected final boolean repeated;
    public final int tag;
    protected final int type;

    private Extension(int n, Class<T> class_, int n2, boolean bl) {
        this.type = n;
        this.clazz = class_;
        this.tag = n2;
        this.repeated = bl;
    }

    @Deprecated
    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T> createMessageTyped(int n, Class<T> class_, int n2) {
        return new Extension<M, T>(n, class_, n2, false);
    }

    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T> createMessageTyped(int n, Class<T> class_, long l) {
        return new Extension<M, T>(n, class_, (int)l, false);
    }

    public static <M extends ExtendableMessageNano<M>, T> Extension<M, T> createPrimitiveTyped(int n, Class<T> class_, long l) {
        return new PrimitiveExtension(n, class_, (int)l, false, 0, 0);
    }

    public static <M extends ExtendableMessageNano<M>, T extends MessageNano> Extension<M, T[]> createRepeatedMessageTyped(int n, Class<T[]> class_, long l) {
        return new Extension<M, T[]>(n, class_, (int)l, true);
    }

    public static <M extends ExtendableMessageNano<M>, T> Extension<M, T> createRepeatedPrimitiveTyped(int n, Class<T> class_, long l, long l2, long l3) {
        return new PrimitiveExtension(n, class_, (int)l, true, (int)l2, (int)l3);
    }

    private T getRepeatedValueFrom(List<UnknownFieldData> object) {
        int n;
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (n = 0; n < object.size(); ++n) {
            UnknownFieldData unknownFieldData = object.get(n);
            if (unknownFieldData.bytes.length == 0) continue;
            this.readDataInto(unknownFieldData, arrayList);
        }
        int n2 = arrayList.size();
        if (n2 == 0) {
            return null;
        }
        object = this.clazz;
        object = ((Class)object).cast(Array.newInstance(((Class)object).getComponentType(), n2));
        for (n = 0; n < n2; ++n) {
            Array.set(object, n, arrayList.get(n));
        }
        return (T)object;
    }

    private T getSingularValueFrom(List<UnknownFieldData> object) {
        if (object.isEmpty()) {
            return null;
        }
        object = object.get(object.size() - 1);
        return this.clazz.cast(this.readData(CodedInputByteBufferNano.newInstance(((UnknownFieldData)object).bytes)));
    }

    protected int computeRepeatedSerializedSize(Object object) {
        int n = 0;
        int n2 = Array.getLength(object);
        for (int i = 0; i < n2; ++i) {
            int n3 = n;
            if (Array.get(object, i) != null) {
                n3 = n + this.computeSingularSerializedSize(Array.get(object, i));
            }
            n = n3;
        }
        return n;
    }

    int computeSerializedSize(Object object) {
        if (this.repeated) {
            return this.computeRepeatedSerializedSize(object);
        }
        return this.computeSingularSerializedSize(object);
    }

    protected int computeSingularSerializedSize(Object object) {
        int n = WireFormatNano.getTagFieldNumber(this.tag);
        int n2 = this.type;
        if (n2 != 10) {
            if (n2 == 11) {
                return CodedOutputByteBufferNano.computeMessageSize(n, (MessageNano)object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown type ");
            ((StringBuilder)object).append(this.type);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return CodedOutputByteBufferNano.computeGroupSize(n, (MessageNano)object);
    }

    final T getValueFrom(List<UnknownFieldData> list) {
        if (list == null) {
            return null;
        }
        list = this.repeated ? this.getRepeatedValueFrom(list) : this.getSingularValueFrom(list);
        return (T)list;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object readData(CodedInputByteBufferNano object) {
        Class<Object> class_ = this.repeated ? this.clazz.getComponentType() : this.clazz;
        try {
            int n = this.type;
            if (n == 10) {
                MessageNano messageNano = (MessageNano)class_.newInstance();
                ((CodedInputByteBufferNano)object).readGroup(messageNano, WireFormatNano.getTagFieldNumber(this.tag));
                return messageNano;
            }
            if (n == 11) {
                MessageNano messageNano = (MessageNano)class_.newInstance();
                ((CodedInputByteBufferNano)object).readMessage(messageNano);
                return messageNano;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown type ");
            ((StringBuilder)object).append(this.type);
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)object).toString());
            throw illegalArgumentException;
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("Error reading extension field", iOException);
        }
        catch (IllegalAccessException illegalAccessException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error creating instance of class ");
            stringBuilder.append(class_);
            throw new IllegalArgumentException(stringBuilder.toString(), illegalAccessException);
        }
        catch (InstantiationException instantiationException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error creating instance of class ");
            stringBuilder.append(class_);
            throw new IllegalArgumentException(stringBuilder.toString(), instantiationException);
        }
    }

    protected void readDataInto(UnknownFieldData unknownFieldData, List<Object> list) {
        list.add(this.readData(CodedInputByteBufferNano.newInstance(unknownFieldData.bytes)));
    }

    protected void writeRepeatedData(Object object, CodedOutputByteBufferNano codedOutputByteBufferNano) {
        int n = Array.getLength(object);
        for (int i = 0; i < n; ++i) {
            Object object2 = Array.get(object, i);
            if (object2 == null) continue;
            this.writeSingularData(object2, codedOutputByteBufferNano);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void writeSingularData(Object object, CodedOutputByteBufferNano object2) {
        try {
            ((CodedOutputByteBufferNano)object2).writeRawVarint32(this.tag);
            int n = this.type;
            if (n == 10) {
                object = (MessageNano)object;
                n = WireFormatNano.getTagFieldNumber(this.tag);
                ((CodedOutputByteBufferNano)object2).writeGroupNoTag((MessageNano)object);
                ((CodedOutputByteBufferNano)object2).writeTag(n, 4);
                return;
            }
            if (n == 11) {
                ((CodedOutputByteBufferNano)object2).writeMessageNoTag((MessageNano)object);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown type ");
            ((StringBuilder)object).append(this.type);
            object2 = new IllegalArgumentException(((StringBuilder)object).toString());
            throw object2;
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException);
        }
    }

    void writeTo(Object object, CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (this.repeated) {
            this.writeRepeatedData(object, codedOutputByteBufferNano);
        } else {
            this.writeSingularData(object, codedOutputByteBufferNano);
        }
    }

    private static class PrimitiveExtension<M extends ExtendableMessageNano<M>, T>
    extends Extension<M, T> {
        private final int nonPackedTag;
        private final int packedTag;

        public PrimitiveExtension(int n, Class<T> class_, int n2, boolean bl, int n3, int n4) {
            super(n, class_, n2, bl);
            this.nonPackedTag = n3;
            this.packedTag = n4;
        }

        private int computePackedDataSize(Object object) {
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = Array.getLength(object);
            switch (this.type) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unexpected non-packable type ");
                    ((StringBuilder)object).append(this.type);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                case 18: {
                    for (int i = 0; i < n8; ++i) {
                        n7 += CodedOutputByteBufferNano.computeSInt64SizeNoTag(Array.getLong(object, i));
                    }
                    break;
                }
                case 17: {
                    n7 = n;
                    for (int i = 0; i < n8; ++i) {
                        n7 += CodedOutputByteBufferNano.computeSInt32SizeNoTag(Array.getInt(object, i));
                    }
                    break;
                }
                case 14: {
                    n7 = n2;
                    for (int i = 0; i < n8; ++i) {
                        n7 += CodedOutputByteBufferNano.computeEnumSizeNoTag(Array.getInt(object, i));
                    }
                    break;
                }
                case 13: {
                    n7 = n3;
                    for (int i = 0; i < n8; ++i) {
                        n7 += CodedOutputByteBufferNano.computeUInt32SizeNoTag(Array.getInt(object, i));
                    }
                    break;
                }
                case 8: {
                    n7 = n8;
                    break;
                }
                case 5: {
                    n7 = n4;
                    for (int i = 0; i < n8; ++i) {
                        n7 += CodedOutputByteBufferNano.computeInt32SizeNoTag(Array.getInt(object, i));
                    }
                    break;
                }
                case 4: {
                    n7 = n5;
                    for (int i = 0; i < n8; ++i) {
                        n7 += CodedOutputByteBufferNano.computeUInt64SizeNoTag(Array.getLong(object, i));
                    }
                    break;
                }
                case 3: {
                    n7 = n6;
                    for (int i = 0; i < n8; ++i) {
                        n7 += CodedOutputByteBufferNano.computeInt64SizeNoTag(Array.getLong(object, i));
                    }
                    break;
                }
                case 2: 
                case 7: 
                case 15: {
                    n7 = n8 * 4;
                    break;
                }
                case 1: 
                case 6: 
                case 16: {
                    n7 = n8 * 8;
                }
            }
            return n7;
        }

        @Override
        protected int computeRepeatedSerializedSize(Object object) {
            if (this.tag == this.nonPackedTag) {
                return super.computeRepeatedSerializedSize(object);
            }
            if (this.tag == this.packedTag) {
                int n = this.computePackedDataSize(object);
                int n2 = CodedOutputByteBufferNano.computeRawVarint32Size(n);
                return CodedOutputByteBufferNano.computeRawVarint32Size(this.tag) + (n2 + n);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected repeated extension tag ");
            ((StringBuilder)object).append(this.tag);
            ((StringBuilder)object).append(", unequal to both non-packed variant ");
            ((StringBuilder)object).append(this.nonPackedTag);
            ((StringBuilder)object).append(" and packed variant ");
            ((StringBuilder)object).append(this.packedTag);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @Override
        protected final int computeSingularSerializedSize(Object object) {
            int n = WireFormatNano.getTagFieldNumber(this.tag);
            switch (this.type) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown type ");
                    ((StringBuilder)object).append(this.type);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                case 18: {
                    return CodedOutputByteBufferNano.computeSInt64Size(n, (Long)object);
                }
                case 17: {
                    return CodedOutputByteBufferNano.computeSInt32Size(n, (Integer)object);
                }
                case 16: {
                    object = (Long)object;
                    return CodedOutputByteBufferNano.computeSFixed64Size(n, (Long)object);
                }
                case 15: {
                    object = (Integer)object;
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

        @Override
        protected Object readData(CodedInputByteBufferNano object) {
            try {
                object = ((CodedInputByteBufferNano)object).readPrimitiveField(this.type);
                return object;
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("Error reading extension field", iOException);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected void readDataInto(UnknownFieldData object, List<Object> list) {
            if (((UnknownFieldData)object).tag == this.nonPackedTag) {
                list.add(this.readData(CodedInputByteBufferNano.newInstance(((UnknownFieldData)object).bytes)));
                return;
            }
            object = CodedInputByteBufferNano.newInstance(((UnknownFieldData)object).bytes);
            try {
                ((CodedInputByteBufferNano)object).pushLimit(((CodedInputByteBufferNano)object).readRawVarint32());
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("Error reading extension field", iOException);
            }
            while (!((CodedInputByteBufferNano)object).isAtEnd()) {
                list.add(this.readData((CodedInputByteBufferNano)object));
            }
            return;
        }

        /*
         * Exception decompiling
         */
        @Override
        protected void writeRepeatedData(Object var1_1, CodedOutputByteBufferNano var2_3) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 19[CASE]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        @Override
        protected final void writeSingularData(Object object, CodedOutputByteBufferNano object2) {
            try {
                block20 : {
                    ((CodedOutputByteBufferNano)object2).writeRawVarint32(this.tag);
                    switch (this.type) {
                        default: {
                            break block20;
                        }
                        case 18: {
                            ((CodedOutputByteBufferNano)object2).writeSInt64NoTag((Long)object);
                            break;
                        }
                        case 17: {
                            ((CodedOutputByteBufferNano)object2).writeSInt32NoTag((Integer)object);
                            break;
                        }
                        case 16: {
                            ((CodedOutputByteBufferNano)object2).writeSFixed64NoTag((Long)object);
                            break;
                        }
                        case 15: {
                            ((CodedOutputByteBufferNano)object2).writeSFixed32NoTag((Integer)object);
                            break;
                        }
                        case 14: {
                            ((CodedOutputByteBufferNano)object2).writeEnumNoTag((Integer)object);
                            break;
                        }
                        case 13: {
                            ((CodedOutputByteBufferNano)object2).writeUInt32NoTag((Integer)object);
                            break;
                        }
                        case 12: {
                            ((CodedOutputByteBufferNano)object2).writeBytesNoTag((byte[])object);
                            break;
                        }
                        case 9: {
                            ((CodedOutputByteBufferNano)object2).writeStringNoTag((String)object);
                            break;
                        }
                        case 8: {
                            ((CodedOutputByteBufferNano)object2).writeBoolNoTag((Boolean)object);
                            break;
                        }
                        case 7: {
                            ((CodedOutputByteBufferNano)object2).writeFixed32NoTag((Integer)object);
                            break;
                        }
                        case 6: {
                            ((CodedOutputByteBufferNano)object2).writeFixed64NoTag((Long)object);
                            break;
                        }
                        case 5: {
                            ((CodedOutputByteBufferNano)object2).writeInt32NoTag((Integer)object);
                            break;
                        }
                        case 4: {
                            ((CodedOutputByteBufferNano)object2).writeUInt64NoTag((Long)object);
                            break;
                        }
                        case 3: {
                            ((CodedOutputByteBufferNano)object2).writeInt64NoTag((Long)object);
                            break;
                        }
                        case 2: {
                            ((CodedOutputByteBufferNano)object2).writeFloatNoTag(((Float)object).floatValue());
                            break;
                        }
                        case 1: {
                            ((CodedOutputByteBufferNano)object2).writeDoubleNoTag((Double)object);
                        }
                    }
                    return;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unknown type ");
                ((StringBuilder)object2).append(this.type);
                object = new IllegalArgumentException(((StringBuilder)object2).toString());
                throw object;
            }
            catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }
    }

}

