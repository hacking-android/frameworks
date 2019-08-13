/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.AbstractMessageLite;
import com.android.framework.protobuf.AbstractParser;
import com.android.framework.protobuf.BooleanArrayList;
import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.CodedOutputStream;
import com.android.framework.protobuf.DoubleArrayList;
import com.android.framework.protobuf.ExtensionLite;
import com.android.framework.protobuf.ExtensionRegistryLite;
import com.android.framework.protobuf.FieldSet;
import com.android.framework.protobuf.FloatArrayList;
import com.android.framework.protobuf.IntArrayList;
import com.android.framework.protobuf.Internal;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.framework.protobuf.LazyFieldLite;
import com.android.framework.protobuf.LongArrayList;
import com.android.framework.protobuf.MapFieldLite;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.MessageLiteOrBuilder;
import com.android.framework.protobuf.MessageLiteToString;
import com.android.framework.protobuf.Parser;
import com.android.framework.protobuf.ProtobufArrayList;
import com.android.framework.protobuf.UninitializedMessageException;
import com.android.framework.protobuf.UnknownFieldSetLite;
import com.android.framework.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class GeneratedMessageLite<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>>
extends AbstractMessageLite<MessageType, BuilderType> {
    protected int memoizedSerializedSize = -1;
    protected UnknownFieldSetLite unknownFields = UnknownFieldSetLite.getDefaultInstance();

    private static <MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>, T> GeneratedExtension<MessageType, T> checkIsLite(ExtensionLite<MessageType, T> extensionLite) {
        if (extensionLite.isLite()) {
            return (GeneratedExtension)extensionLite;
        }
        throw new IllegalArgumentException("Expected a lite extension.");
    }

    private static <T extends GeneratedMessageLite<T, ?>> T checkMessageInitialized(T t) throws InvalidProtocolBufferException {
        if (t != null && !((GeneratedMessageLite)t).isInitialized()) {
            throw ((AbstractMessageLite)t).newUninitializedMessageException().asInvalidProtocolBufferException().setUnfinishedMessage(t);
        }
        return t;
    }

    protected static Internal.BooleanList emptyBooleanList() {
        return BooleanArrayList.emptyList();
    }

    protected static Internal.DoubleList emptyDoubleList() {
        return DoubleArrayList.emptyList();
    }

    protected static Internal.FloatList emptyFloatList() {
        return FloatArrayList.emptyList();
    }

    protected static Internal.IntList emptyIntList() {
        return IntArrayList.emptyList();
    }

    protected static Internal.LongList emptyLongList() {
        return LongArrayList.emptyList();
    }

    protected static <E> Internal.ProtobufList<E> emptyProtobufList() {
        return ProtobufArrayList.emptyList();
    }

    private final void ensureUnknownFieldsInitialized() {
        if (this.unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            this.unknownFields = UnknownFieldSetLite.newInstance();
        }
    }

    static Method getMethodOrDie(Class class_, String string2, Class ... object) {
        try {
            object = class_.getMethod(string2, (Class<?>)object);
            return object;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Generated message class \"");
            ((StringBuilder)object).append(class_.getName());
            ((StringBuilder)object).append("\" missing method \"");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("\".");
            throw new RuntimeException(((StringBuilder)object).toString(), noSuchMethodException);
        }
    }

    static Object invokeOrDie(Method object, Object object2, Object ... arrobject) {
        try {
            object = ((Method)object).invoke(object2, arrobject);
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getCause();
            if (!(throwable instanceof RuntimeException)) {
                if (throwable instanceof Error) {
                    throw (Error)throwable;
                }
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", throwable);
            }
            throw (RuntimeException)throwable;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", illegalAccessException);
        }
    }

    protected static final <T extends GeneratedMessageLite<T, ?>> boolean isInitialized(T t, boolean bl) {
        bl = ((GeneratedMessageLite)t).dynamicMethod(MethodToInvoke.IS_INITIALIZED, bl) != null;
        return bl;
    }

    protected static final <T extends GeneratedMessageLite<T, ?>> void makeImmutable(T t) {
        ((GeneratedMessageLite)t).dynamicMethod(MethodToInvoke.MAKE_IMMUTABLE);
    }

    protected static Internal.BooleanList mutableCopy(Internal.BooleanList booleanList) {
        int n = booleanList.size();
        n = n == 0 ? 10 : (n *= 2);
        return booleanList.mutableCopyWithCapacity(n);
    }

    protected static Internal.DoubleList mutableCopy(Internal.DoubleList doubleList) {
        int n = doubleList.size();
        n = n == 0 ? 10 : (n *= 2);
        return doubleList.mutableCopyWithCapacity(n);
    }

    protected static Internal.FloatList mutableCopy(Internal.FloatList floatList) {
        int n = floatList.size();
        n = n == 0 ? 10 : (n *= 2);
        return floatList.mutableCopyWithCapacity(n);
    }

    protected static Internal.IntList mutableCopy(Internal.IntList intList) {
        int n = intList.size();
        n = n == 0 ? 10 : (n *= 2);
        return intList.mutableCopyWithCapacity(n);
    }

    protected static Internal.LongList mutableCopy(Internal.LongList longList) {
        int n = longList.size();
        n = n == 0 ? 10 : (n *= 2);
        return longList.mutableCopyWithCapacity(n);
    }

    protected static <E> Internal.ProtobufList<E> mutableCopy(Internal.ProtobufList<E> protobufList) {
        int n = protobufList.size();
        n = n == 0 ? 10 : (n *= 2);
        return protobufList.mutableCopyWithCapacity(n);
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType ContainingType, MessageLite messageLite, Internal.EnumLiteMap<?> enumLiteMap, int n, WireFormat.FieldType fieldType, boolean bl, Class class_) {
        return new GeneratedExtension(ContainingType, Collections.emptyList(), messageLite, new ExtensionDescriptor(enumLiteMap, n, fieldType, true, bl), class_);
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(ContainingType ContainingType, Type Type2, MessageLite messageLite, Internal.EnumLiteMap<?> enumLiteMap, int n, WireFormat.FieldType fieldType, Class class_) {
        return new GeneratedExtension<ContainingType, Type>(ContainingType, Type2, messageLite, new ExtensionDescriptor(enumLiteMap, n, fieldType, false, false), class_);
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T t, InputStream inputStream) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.checkMessageInitialized(GeneratedMessageLite.parsePartialDelimitedFrom(t, inputStream, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T t, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.checkMessageInitialized(GeneratedMessageLite.parsePartialDelimitedFrom(t, inputStream, extensionRegistryLite));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, ByteString byteString) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.checkMessageInitialized(GeneratedMessageLite.parseFrom(t, byteString, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.checkMessageInitialized(GeneratedMessageLite.parsePartialFrom(t, byteString, extensionRegistryLite));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parseFrom(t, codedInputStream, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.checkMessageInitialized(GeneratedMessageLite.parsePartialFrom(t, codedInputStream, extensionRegistryLite));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, InputStream inputStream) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.checkMessageInitialized(GeneratedMessageLite.parsePartialFrom(t, CodedInputStream.newInstance(inputStream), ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.checkMessageInitialized(GeneratedMessageLite.parsePartialFrom(t, CodedInputStream.newInstance(inputStream), extensionRegistryLite));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, byte[] arrby) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.checkMessageInitialized(GeneratedMessageLite.parsePartialFrom(t, arrby, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, byte[] arrby, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.checkMessageInitialized(GeneratedMessageLite.parsePartialFrom(t, arrby, extensionRegistryLite));
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialDelimitedFrom(T t, InputStream object, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        int n;
        block5 : {
            try {
                n = ((InputStream)object).read();
                if (n != -1) break block5;
            }
            catch (IOException iOException) {
                throw new InvalidProtocolBufferException(iOException.getMessage());
            }
            return null;
        }
        n = CodedInputStream.readRawVarint32(n, (InputStream)object);
        object = CodedInputStream.newInstance(new AbstractMessageLite.Builder.LimitedInputStream((InputStream)object, n));
        t = GeneratedMessageLite.parsePartialFrom(t, (CodedInputStream)object, extensionRegistryLite);
        try {
            ((CodedInputStream)object).checkLastTagWas(0);
        }
        catch (InvalidProtocolBufferException invalidProtocolBufferException) {
            throw invalidProtocolBufferException.setUnfinishedMessage(t);
        }
        return t;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, ByteString object, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        object = ((ByteString)object).newCodedInput();
        t = GeneratedMessageLite.parsePartialFrom(t, (CodedInputStream)object, extensionRegistryLite);
        {
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException;
            }
        }
        try {
            ((CodedInputStream)object).checkLastTagWas(0);
        }
        catch (InvalidProtocolBufferException invalidProtocolBufferException) {
            throw invalidProtocolBufferException.setUnfinishedMessage(t);
        }
        return t;
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return GeneratedMessageLite.parsePartialFrom(t, codedInputStream, ExtensionRegistryLite.getEmptyRegistry());
    }

    static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T object, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        object = (GeneratedMessageLite)((GeneratedMessageLite)object).dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
        try {
            ((GeneratedMessageLite)object).dynamicMethod(MethodToInvoke.MERGE_FROM_STREAM, codedInputStream, extensionRegistryLite);
            ((GeneratedMessageLite)object).makeImmutable();
        }
        catch (RuntimeException runtimeException) {
            if (runtimeException.getCause() instanceof InvalidProtocolBufferException) {
                throw (InvalidProtocolBufferException)runtimeException.getCause();
            }
            throw runtimeException;
        }
        return (T)object;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, byte[] object, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        object = CodedInputStream.newInstance((byte[])object);
        t = GeneratedMessageLite.parsePartialFrom(t, (CodedInputStream)object, extensionRegistryLite);
        {
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException;
            }
        }
        try {
            ((CodedInputStream)object).checkLastTagWas(0);
        }
        catch (InvalidProtocolBufferException invalidProtocolBufferException) {
            throw invalidProtocolBufferException.setUnfinishedMessage(t);
        }
        return t;
    }

    protected Object dynamicMethod(MethodToInvoke methodToInvoke) {
        return this.dynamicMethod(methodToInvoke, null, null);
    }

    protected Object dynamicMethod(MethodToInvoke methodToInvoke, Object object) {
        return this.dynamicMethod(methodToInvoke, object, null);
    }

    protected abstract Object dynamicMethod(MethodToInvoke var1, Object var2, Object var3);

    boolean equals(EqualsVisitor equalsVisitor, MessageLite messageLite) {
        if (this == messageLite) {
            return true;
        }
        if (!this.getDefaultInstanceForType().getClass().isInstance(messageLite)) {
            return false;
        }
        this.visit(equalsVisitor, (GeneratedMessageLite)messageLite);
        return true;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!this.getDefaultInstanceForType().getClass().isInstance(object)) {
            return false;
        }
        try {
            this.visit(EqualsVisitor.INSTANCE, (GeneratedMessageLite)object);
            return true;
        }
        catch (EqualsVisitor.NotEqualsException notEqualsException) {
            return false;
        }
    }

    public final MessageType getDefaultInstanceForType() {
        return (MessageType)((GeneratedMessageLite)this.dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE));
    }

    public final Parser<MessageType> getParserForType() {
        return (Parser)this.dynamicMethod(MethodToInvoke.GET_PARSER);
    }

    public int hashCode() {
        if (this.memoizedHashCode == 0) {
            HashCodeVisitor hashCodeVisitor = new HashCodeVisitor();
            this.visit(hashCodeVisitor, this);
            this.memoizedHashCode = hashCodeVisitor.hashCode;
        }
        return this.memoizedHashCode;
    }

    int hashCode(HashCodeVisitor hashCodeVisitor) {
        if (this.memoizedHashCode == 0) {
            int n = hashCodeVisitor.hashCode;
            hashCodeVisitor.hashCode = 0;
            this.visit(hashCodeVisitor, this);
            this.memoizedHashCode = hashCodeVisitor.hashCode;
            hashCodeVisitor.hashCode = n;
        }
        return this.memoizedHashCode;
    }

    @Override
    public final boolean isInitialized() {
        boolean bl = this.dynamicMethod(MethodToInvoke.IS_INITIALIZED, Boolean.TRUE) != null;
        return bl;
    }

    protected void makeImmutable() {
        this.dynamicMethod(MethodToInvoke.MAKE_IMMUTABLE);
        this.unknownFields.makeImmutable();
    }

    protected void mergeLengthDelimitedField(int n, ByteString byteString) {
        this.ensureUnknownFieldsInitialized();
        this.unknownFields.mergeLengthDelimitedField(n, byteString);
    }

    protected final void mergeUnknownFields(UnknownFieldSetLite unknownFieldSetLite) {
        this.unknownFields = UnknownFieldSetLite.mutableCopyOf(this.unknownFields, unknownFieldSetLite);
    }

    protected void mergeVarintField(int n, int n2) {
        this.ensureUnknownFieldsInitialized();
        this.unknownFields.mergeVarintField(n, n2);
    }

    public final BuilderType newBuilderForType() {
        return (BuilderType)((Builder)this.dynamicMethod(MethodToInvoke.NEW_BUILDER));
    }

    protected boolean parseUnknownField(int n, CodedInputStream codedInputStream) throws IOException {
        if (WireFormat.getTagWireType(n) == 4) {
            return false;
        }
        this.ensureUnknownFieldsInitialized();
        return this.unknownFields.mergeFieldFrom(n, codedInputStream);
    }

    public final BuilderType toBuilder() {
        Builder builder = (Builder)this.dynamicMethod(MethodToInvoke.NEW_BUILDER);
        builder.mergeFrom(this);
        return (BuilderType)builder;
    }

    public String toString() {
        return MessageLiteToString.toString(this, Object.super.toString());
    }

    void visit(Visitor visitor, MessageType MessageType2) {
        this.dynamicMethod(MethodToInvoke.VISIT, visitor, MessageType2);
        this.unknownFields = visitor.visitUnknownFields(this.unknownFields, ((GeneratedMessageLite)MessageType2).unknownFields);
    }

    public static abstract class Builder<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>>
    extends AbstractMessageLite.Builder<MessageType, BuilderType> {
        private final MessageType defaultInstance;
        protected MessageType instance;
        protected boolean isBuilt;

        protected Builder(MessageType MessageType2) {
            this.defaultInstance = MessageType2;
            this.instance = (GeneratedMessageLite)((GeneratedMessageLite)MessageType2).dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
            this.isBuilt = false;
        }

        public final MessageType build() {
            MessageLite messageLite = this.buildPartial();
            if (((GeneratedMessageLite)messageLite).isInitialized()) {
                return (MessageType)messageLite;
            }
            throw Builder.newUninitializedMessageException(messageLite);
        }

        public MessageType buildPartial() {
            if (this.isBuilt) {
                return this.instance;
            }
            ((GeneratedMessageLite)this.instance).makeImmutable();
            this.isBuilt = true;
            return this.instance;
        }

        public final BuilderType clear() {
            this.instance = (GeneratedMessageLite)((GeneratedMessageLite)this.instance).dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
            return (BuilderType)this;
        }

        @Override
        public BuilderType clone() {
            MessageLite.Builder builder = ((GeneratedMessageLite)this.getDefaultInstanceForType()).newBuilderForType();
            ((Builder)builder).mergeFrom(this.buildPartial());
            return (BuilderType)builder;
        }

        protected void copyOnWrite() {
            if (this.isBuilt) {
                GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite)((GeneratedMessageLite)this.instance).dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
                generatedMessageLite.visit(MergeFromVisitor.INSTANCE, this.instance);
                this.instance = generatedMessageLite;
                this.isBuilt = false;
            }
        }

        public MessageType getDefaultInstanceForType() {
            return this.defaultInstance;
        }

        @Override
        protected BuilderType internalMergeFrom(MessageType MessageType2) {
            return this.mergeFrom(MessageType2);
        }

        @Override
        public final boolean isInitialized() {
            return GeneratedMessageLite.isInitialized(this.instance, false);
        }

        @Override
        public BuilderType mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            this.copyOnWrite();
            try {
                ((GeneratedMessageLite)this.instance).dynamicMethod(MethodToInvoke.MERGE_FROM_STREAM, codedInputStream, extensionRegistryLite);
            }
            catch (RuntimeException runtimeException) {
                if (runtimeException.getCause() instanceof IOException) {
                    throw (IOException)runtimeException.getCause();
                }
                throw runtimeException;
            }
            return (BuilderType)this;
        }

        public BuilderType mergeFrom(MessageType MessageType2) {
            this.copyOnWrite();
            ((GeneratedMessageLite)this.instance).visit(MergeFromVisitor.INSTANCE, MessageType2);
            return (BuilderType)this;
        }
    }

    protected static class DefaultInstanceBasedParser<T extends GeneratedMessageLite<T, ?>>
    extends AbstractParser<T> {
        private T defaultInstance;

        public DefaultInstanceBasedParser(T t) {
            this.defaultInstance = t;
        }

        @Override
        public T parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parsePartialFrom(this.defaultInstance, codedInputStream, extensionRegistryLite);
        }
    }

    static class EqualsVisitor
    implements Visitor {
        static final EqualsVisitor INSTANCE = new EqualsVisitor();
        static final NotEqualsException NOT_EQUALS = new NotEqualsException();

        private EqualsVisitor() {
        }

        @Override
        public boolean visitBoolean(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
            if (bl == bl3 && bl2 == bl4) {
                return bl2;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Internal.BooleanList visitBooleanList(Internal.BooleanList booleanList, Internal.BooleanList booleanList2) {
            if (booleanList.equals(booleanList2)) {
                return booleanList;
            }
            throw NOT_EQUALS;
        }

        @Override
        public ByteString visitByteString(boolean bl, ByteString byteString, boolean bl2, ByteString byteString2) {
            if (bl == bl2 && byteString.equals(byteString2)) {
                return byteString;
            }
            throw NOT_EQUALS;
        }

        @Override
        public double visitDouble(boolean bl, double d, boolean bl2, double d2) {
            if (bl == bl2 && d == d2) {
                return d;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Internal.DoubleList visitDoubleList(Internal.DoubleList doubleList, Internal.DoubleList doubleList2) {
            if (doubleList.equals(doubleList2)) {
                return doubleList;
            }
            throw NOT_EQUALS;
        }

        @Override
        public FieldSet<ExtensionDescriptor> visitExtensions(FieldSet<ExtensionDescriptor> fieldSet, FieldSet<ExtensionDescriptor> fieldSet2) {
            if (fieldSet.equals(fieldSet2)) {
                return fieldSet;
            }
            throw NOT_EQUALS;
        }

        @Override
        public float visitFloat(boolean bl, float f, boolean bl2, float f2) {
            if (bl == bl2 && f == f2) {
                return f;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Internal.FloatList visitFloatList(Internal.FloatList floatList, Internal.FloatList floatList2) {
            if (floatList.equals(floatList2)) {
                return floatList;
            }
            throw NOT_EQUALS;
        }

        @Override
        public int visitInt(boolean bl, int n, boolean bl2, int n2) {
            if (bl == bl2 && n == n2) {
                return n;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Internal.IntList visitIntList(Internal.IntList intList, Internal.IntList intList2) {
            if (intList.equals(intList2)) {
                return intList;
            }
            throw NOT_EQUALS;
        }

        @Override
        public LazyFieldLite visitLazyMessage(boolean bl, LazyFieldLite lazyFieldLite, boolean bl2, LazyFieldLite lazyFieldLite2) {
            if (!bl && !bl2) {
                return lazyFieldLite;
            }
            if (bl && bl2 && lazyFieldLite.equals(lazyFieldLite2)) {
                return lazyFieldLite;
            }
            throw NOT_EQUALS;
        }

        @Override
        public <T> Internal.ProtobufList<T> visitList(Internal.ProtobufList<T> protobufList, Internal.ProtobufList<T> protobufList2) {
            if (protobufList.equals(protobufList2)) {
                return protobufList;
            }
            throw NOT_EQUALS;
        }

        @Override
        public long visitLong(boolean bl, long l, boolean bl2, long l2) {
            if (bl == bl2 && l == l2) {
                return l;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Internal.LongList visitLongList(Internal.LongList longList, Internal.LongList longList2) {
            if (longList.equals(longList2)) {
                return longList;
            }
            throw NOT_EQUALS;
        }

        @Override
        public <K, V> MapFieldLite<K, V> visitMap(MapFieldLite<K, V> mapFieldLite, MapFieldLite<K, V> mapFieldLite2) {
            if (mapFieldLite.equals(mapFieldLite2)) {
                return mapFieldLite;
            }
            throw NOT_EQUALS;
        }

        @Override
        public <T extends MessageLite> T visitMessage(T t, T t2) {
            if (t == null && t2 == null) {
                return null;
            }
            if (t != null && t2 != null) {
                ((GeneratedMessageLite)t).equals(this, t2);
                return t;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Object visitOneofBoolean(boolean bl, Object object, Object object2) {
            if (bl && object.equals(object2)) {
                return object;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Object visitOneofByteString(boolean bl, Object object, Object object2) {
            if (bl && object.equals(object2)) {
                return object;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Object visitOneofDouble(boolean bl, Object object, Object object2) {
            if (bl && object.equals(object2)) {
                return object;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Object visitOneofFloat(boolean bl, Object object, Object object2) {
            if (bl && object.equals(object2)) {
                return object;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Object visitOneofInt(boolean bl, Object object, Object object2) {
            if (bl && object.equals(object2)) {
                return object;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Object visitOneofLazyMessage(boolean bl, Object object, Object object2) {
            if (bl && object.equals(object2)) {
                return object;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Object visitOneofLong(boolean bl, Object object, Object object2) {
            if (bl && object.equals(object2)) {
                return object;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Object visitOneofMessage(boolean bl, Object object, Object object2) {
            if (bl && ((GeneratedMessageLite)object).equals(this, (MessageLite)object2)) {
                return object;
            }
            throw NOT_EQUALS;
        }

        @Override
        public void visitOneofNotSet(boolean bl) {
            if (!bl) {
                return;
            }
            throw NOT_EQUALS;
        }

        @Override
        public Object visitOneofString(boolean bl, Object object, Object object2) {
            if (bl && object.equals(object2)) {
                return object;
            }
            throw NOT_EQUALS;
        }

        @Override
        public String visitString(boolean bl, String string2, boolean bl2, String string3) {
            if (bl == bl2 && string2.equals(string3)) {
                return string2;
            }
            throw NOT_EQUALS;
        }

        @Override
        public UnknownFieldSetLite visitUnknownFields(UnknownFieldSetLite unknownFieldSetLite, UnknownFieldSetLite unknownFieldSetLite2) {
            if (unknownFieldSetLite.equals(unknownFieldSetLite2)) {
                return unknownFieldSetLite;
            }
            throw NOT_EQUALS;
        }

        static final class NotEqualsException
        extends RuntimeException {
            NotEqualsException() {
            }
        }

    }

    public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>>
    extends Builder<MessageType, BuilderType>
    implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected ExtendableBuilder(MessageType MessageType2) {
            super(MessageType2);
            ((ExtendableMessage)this.instance).extensions = ((ExtendableMessage)this.instance).extensions.clone();
        }

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> generatedExtension) {
            if (generatedExtension.getContainingTypeDefaultInstance() == this.getDefaultInstanceForType()) {
                return;
            }
            throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
        }

        public final <Type> BuilderType addExtension(ExtensionLite<MessageType, List<Type>> generatedExtension, Type Type2) {
            generatedExtension = GeneratedMessageLite.checkIsLite(generatedExtension);
            this.verifyExtensionContainingType(generatedExtension);
            this.copyOnWrite();
            ((ExtendableMessage)this.instance).extensions.addRepeatedField(generatedExtension.descriptor, generatedExtension.singularToFieldSetType(Type2));
            return (BuilderType)this;
        }

        @Override
        public final MessageType buildPartial() {
            if (this.isBuilt) {
                return (MessageType)((ExtendableMessage)this.instance);
            }
            ((ExtendableMessage)this.instance).extensions.makeImmutable();
            return (MessageType)((ExtendableMessage)super.buildPartial());
        }

        public final <Type> BuilderType clearExtension(ExtensionLite<MessageType, ?> generatedExtension) {
            generatedExtension = GeneratedMessageLite.checkIsLite(generatedExtension);
            this.verifyExtensionContainingType(generatedExtension);
            this.copyOnWrite();
            ((ExtendableMessage)this.instance).extensions.clearField(generatedExtension.descriptor);
            return (BuilderType)this;
        }

        @Override
        public BuilderType clone() {
            return (BuilderType)((ExtendableBuilder)super.clone());
        }

        @Override
        protected void copyOnWrite() {
            if (!this.isBuilt) {
                return;
            }
            super.copyOnWrite();
            ((ExtendableMessage)this.instance).extensions = ((ExtendableMessage)this.instance).extensions.clone();
        }

        @Override
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            return ((ExtendableMessage)this.instance).getExtension(extensionLite);
        }

        @Override
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int n) {
            return ((ExtendableMessage)this.instance).getExtension(extensionLite, n);
        }

        @Override
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite) {
            return ((ExtendableMessage)this.instance).getExtensionCount(extensionLite);
        }

        @Override
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite) {
            return ((ExtendableMessage)this.instance).hasExtension(extensionLite);
        }

        void internalSetExtensionSet(FieldSet<ExtensionDescriptor> fieldSet) {
            this.copyOnWrite();
            ((ExtendableMessage)this.instance).extensions = fieldSet;
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, List<Type>> generatedExtension, int n, Type Type2) {
            generatedExtension = GeneratedMessageLite.checkIsLite(generatedExtension);
            this.verifyExtensionContainingType(generatedExtension);
            this.copyOnWrite();
            ((ExtendableMessage)this.instance).extensions.setRepeatedField(generatedExtension.descriptor, n, generatedExtension.singularToFieldSetType(Type2));
            return (BuilderType)this;
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, Type> generatedExtension, Type Type2) {
            generatedExtension = GeneratedMessageLite.checkIsLite(generatedExtension);
            this.verifyExtensionContainingType(generatedExtension);
            this.copyOnWrite();
            ((ExtendableMessage)this.instance).extensions.setField(generatedExtension.descriptor, generatedExtension.toFieldSetType(Type2));
            return (BuilderType)this;
        }
    }

    public static abstract class ExtendableMessage<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>>
    extends GeneratedMessageLite<MessageType, BuilderType>
    implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected FieldSet<ExtensionDescriptor> extensions = FieldSet.newFieldSet();

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> generatedExtension) {
            if (generatedExtension.getContainingTypeDefaultInstance() == this.getDefaultInstanceForType()) {
                return;
            }
            throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        protected int extensionsSerializedSize() {
            return this.extensions.getSerializedSize();
        }

        protected int extensionsSerializedSizeAsMessageSet() {
            return this.extensions.getMessageSetSerializedSize();
        }

        @Override
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> object) {
            GeneratedExtension generatedExtension = GeneratedMessageLite.checkIsLite(object);
            this.verifyExtensionContainingType(generatedExtension);
            object = this.extensions.getField(generatedExtension.descriptor);
            if (object == null) {
                return generatedExtension.defaultValue;
            }
            return (Type)generatedExtension.fromFieldSetType(object);
        }

        @Override
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> generatedExtension, int n) {
            generatedExtension = GeneratedMessageLite.checkIsLite(generatedExtension);
            this.verifyExtensionContainingType(generatedExtension);
            return (Type)generatedExtension.singularFromFieldSetType(this.extensions.getRepeatedField(generatedExtension.descriptor, n));
        }

        @Override
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> generatedExtension) {
            generatedExtension = GeneratedMessageLite.checkIsLite(generatedExtension);
            this.verifyExtensionContainingType(generatedExtension);
            return this.extensions.getRepeatedFieldCount(generatedExtension.descriptor);
        }

        @Override
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> generatedExtension) {
            generatedExtension = GeneratedMessageLite.checkIsLite(generatedExtension);
            this.verifyExtensionContainingType(generatedExtension);
            return this.extensions.hasField(generatedExtension.descriptor);
        }

        @Override
        protected final void makeImmutable() {
            super.makeImmutable();
            this.extensions.makeImmutable();
        }

        protected final void mergeExtensionFields(MessageType MessageType2) {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.clone();
            }
            this.extensions.mergeFrom(((ExtendableMessage)MessageType2).extensions);
        }

        protected ExtendableMessage<MessageType, BuilderType> newExtensionWriter() {
            return new ExtensionWriter(false);
        }

        protected ExtendableMessage<MessageType, BuilderType> newMessageSetExtensionWriter() {
            return new ExtensionWriter(true);
        }

        protected <MessageType extends MessageLite> boolean parseUnknownField(MessageType object, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, int n) throws IOException {
            int n2 = WireFormat.getTagWireType(n);
            int n3 = WireFormat.getTagFieldNumber(n);
            GeneratedExtension<MessageType, ?> generatedExtension = extensionRegistryLite.findLiteExtensionByNumber(object, n3);
            int n4 = 0;
            int n5 = 0;
            if (generatedExtension == null) {
                n4 = 1;
            } else if (n2 == FieldSet.getWireFormatForFieldType(generatedExtension.descriptor.getLiteType(), false)) {
                n5 = 0;
            } else if (generatedExtension.descriptor.isRepeated && generatedExtension.descriptor.type.isPackable() && n2 == FieldSet.getWireFormatForFieldType(generatedExtension.descriptor.getLiteType(), true)) {
                n5 = 1;
            } else {
                n4 = 1;
            }
            if (n4 != 0) {
                return this.parseUnknownField(n, codedInputStream);
            }
            if (n5 != 0) {
                n4 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                if (generatedExtension.descriptor.getLiteType() == WireFormat.FieldType.ENUM) {
                    n = n2;
                    while (codedInputStream.getBytesUntilLimit() > 0) {
                        n5 = codedInputStream.readEnum();
                        object = generatedExtension.descriptor.getEnumType().findValueByNumber(n5);
                        if (object == null) {
                            return true;
                        }
                        this.extensions.addRepeatedField(generatedExtension.descriptor, generatedExtension.singularToFieldSetType(object));
                    }
                } else {
                    while (codedInputStream.getBytesUntilLimit() > 0) {
                        object = FieldSet.readPrimitiveField(codedInputStream, generatedExtension.descriptor.getLiteType(), false);
                        this.extensions.addRepeatedField(generatedExtension.descriptor, object);
                    }
                }
                codedInputStream.popLimit(n4);
            } else {
                n = 1.$SwitchMap$com$google$protobuf$WireFormat$JavaType[generatedExtension.descriptor.getLiteJavaType().ordinal()];
                if (n != 1) {
                    if (n != 2) {
                        object = FieldSet.readPrimitiveField(codedInputStream, generatedExtension.descriptor.getLiteType(), false);
                    } else {
                        n = codedInputStream.readEnum();
                        codedInputStream = generatedExtension.descriptor.getEnumType().findValueByNumber(n);
                        object = codedInputStream;
                        if (codedInputStream == null) {
                            this.mergeVarintField(n3, n);
                            return true;
                        }
                    }
                } else {
                    MessageLite.Builder builder = null;
                    object = builder;
                    if (!generatedExtension.descriptor.isRepeated()) {
                        MessageLite messageLite = (MessageLite)this.extensions.getField(generatedExtension.descriptor);
                        object = builder;
                        if (messageLite != null) {
                            object = messageLite.toBuilder();
                        }
                    }
                    builder = (MessageLite.Builder)object;
                    if (object == null) {
                        builder = generatedExtension.getMessageDefaultInstance().newBuilderForType();
                    }
                    if (generatedExtension.descriptor.getLiteType() == WireFormat.FieldType.GROUP) {
                        codedInputStream.readGroup(generatedExtension.getNumber(), builder, extensionRegistryLite);
                    } else {
                        codedInputStream.readMessage(builder, extensionRegistryLite);
                    }
                    object = builder.build();
                }
                if (generatedExtension.descriptor.isRepeated()) {
                    this.extensions.addRepeatedField(generatedExtension.descriptor, generatedExtension.singularToFieldSetType(object));
                } else {
                    this.extensions.setField(generatedExtension.descriptor, generatedExtension.singularToFieldSetType(object));
                }
            }
            return true;
        }

        @Override
        final void visit(Visitor visitor, MessageType MessageType2) {
            super.visit(visitor, MessageType2);
            this.extensions = visitor.visitExtensions(this.extensions, ((ExtendableMessage)MessageType2).extensions);
        }

        protected class ExtensionWriter {
            private final Iterator<Map.Entry<ExtensionDescriptor, Object>> iter;
            private final boolean messageSetWireFormat;
            private Map.Entry<ExtensionDescriptor, Object> next;

            private ExtensionWriter(boolean bl) {
                this.iter = ExtendableMessage.this.extensions.iterator();
                if (this.iter.hasNext()) {
                    this.next = this.iter.next();
                }
                this.messageSetWireFormat = bl;
            }

            public void writeUntil(int n, CodedOutputStream codedOutputStream) throws IOException {
                Object object;
                while ((object = this.next) != null && object.getKey().getNumber() < n) {
                    object = this.next.getKey();
                    if (this.messageSetWireFormat && ((ExtensionDescriptor)object).getLiteJavaType() == WireFormat.JavaType.MESSAGE && !((ExtensionDescriptor)object).isRepeated()) {
                        codedOutputStream.writeMessageSetExtension(((ExtensionDescriptor)object).getNumber(), (MessageLite)this.next.getValue());
                    } else {
                        FieldSet.writeField(object, this.next.getValue(), codedOutputStream);
                    }
                    if (this.iter.hasNext()) {
                        this.next = this.iter.next();
                        continue;
                    }
                    this.next = null;
                }
            }
        }

    }

    public static interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>>
    extends MessageLiteOrBuilder {
        public <Type> Type getExtension(ExtensionLite<MessageType, Type> var1);

        public <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> var1, int var2);

        public <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> var1);

        public <Type> boolean hasExtension(ExtensionLite<MessageType, Type> var1);
    }

    static final class ExtensionDescriptor
    implements FieldSet.FieldDescriptorLite<ExtensionDescriptor> {
        final Internal.EnumLiteMap<?> enumTypeMap;
        final boolean isPacked;
        final boolean isRepeated;
        final int number;
        final WireFormat.FieldType type;

        ExtensionDescriptor(Internal.EnumLiteMap<?> enumLiteMap, int n, WireFormat.FieldType fieldType, boolean bl, boolean bl2) {
            this.enumTypeMap = enumLiteMap;
            this.number = n;
            this.type = fieldType;
            this.isRepeated = bl;
            this.isPacked = bl2;
        }

        @Override
        public int compareTo(ExtensionDescriptor extensionDescriptor) {
            return this.number - extensionDescriptor.number;
        }

        @Override
        public Internal.EnumLiteMap<?> getEnumType() {
            return this.enumTypeMap;
        }

        @Override
        public WireFormat.JavaType getLiteJavaType() {
            return this.type.getJavaType();
        }

        @Override
        public WireFormat.FieldType getLiteType() {
            return this.type;
        }

        @Override
        public int getNumber() {
            return this.number;
        }

        @Override
        public MessageLite.Builder internalMergeFrom(MessageLite.Builder builder, MessageLite messageLite) {
            return ((Builder)builder).mergeFrom((GeneratedMessageLite)messageLite);
        }

        @Override
        public boolean isPacked() {
            return this.isPacked;
        }

        @Override
        public boolean isRepeated() {
            return this.isRepeated;
        }
    }

    public static class GeneratedExtension<ContainingType extends MessageLite, Type>
    extends ExtensionLite<ContainingType, Type> {
        final ContainingType containingTypeDefaultInstance;
        final Type defaultValue;
        final ExtensionDescriptor descriptor;
        final MessageLite messageDefaultInstance;

        GeneratedExtension(ContainingType ContainingType, Type Type2, MessageLite messageLite, ExtensionDescriptor extensionDescriptor, Class class_) {
            if (ContainingType != null) {
                if (extensionDescriptor.getLiteType() == WireFormat.FieldType.MESSAGE && messageLite == null) {
                    throw new IllegalArgumentException("Null messageDefaultInstance");
                }
                this.containingTypeDefaultInstance = ContainingType;
                this.defaultValue = Type2;
                this.messageDefaultInstance = messageLite;
                this.descriptor = extensionDescriptor;
                return;
            }
            throw new IllegalArgumentException("Null containingTypeDefaultInstance");
        }

        Object fromFieldSetType(Object iterator) {
            if (this.descriptor.isRepeated()) {
                if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                    ArrayList<Object> arrayList = new ArrayList<Object>();
                    iterator = ((List)((Object)iterator)).iterator();
                    while (iterator.hasNext()) {
                        arrayList.add(this.singularFromFieldSetType(iterator.next()));
                    }
                    return arrayList;
                }
                return iterator;
            }
            return this.singularFromFieldSetType(iterator);
        }

        public ContainingType getContainingTypeDefaultInstance() {
            return this.containingTypeDefaultInstance;
        }

        @Override
        public Type getDefaultValue() {
            return this.defaultValue;
        }

        @Override
        public WireFormat.FieldType getLiteType() {
            return this.descriptor.getLiteType();
        }

        @Override
        public MessageLite getMessageDefaultInstance() {
            return this.messageDefaultInstance;
        }

        @Override
        public int getNumber() {
            return this.descriptor.getNumber();
        }

        @Override
        public boolean isRepeated() {
            return this.descriptor.isRepeated;
        }

        Object singularFromFieldSetType(Object object) {
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                return this.descriptor.enumTypeMap.findValueByNumber((Integer)object);
            }
            return object;
        }

        Object singularToFieldSetType(Object object) {
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                return ((Internal.EnumLite)object).getNumber();
            }
            return object;
        }

        Object toFieldSetType(Object iterator) {
            if (this.descriptor.isRepeated()) {
                if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                    ArrayList<Object> arrayList = new ArrayList<Object>();
                    iterator = ((List)((Object)iterator)).iterator();
                    while (iterator.hasNext()) {
                        arrayList.add(this.singularToFieldSetType(iterator.next()));
                    }
                    return arrayList;
                }
                return iterator;
            }
            return this.singularToFieldSetType(iterator);
        }
    }

    private static class HashCodeVisitor
    implements Visitor {
        private int hashCode = 0;

        private HashCodeVisitor() {
        }

        @Override
        public boolean visitBoolean(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
            this.hashCode = this.hashCode * 53 + Internal.hashBoolean(bl2);
            return bl2;
        }

        @Override
        public Internal.BooleanList visitBooleanList(Internal.BooleanList booleanList, Internal.BooleanList booleanList2) {
            this.hashCode = this.hashCode * 53 + booleanList.hashCode();
            return booleanList;
        }

        @Override
        public ByteString visitByteString(boolean bl, ByteString byteString, boolean bl2, ByteString byteString2) {
            this.hashCode = this.hashCode * 53 + byteString.hashCode();
            return byteString;
        }

        @Override
        public double visitDouble(boolean bl, double d, boolean bl2, double d2) {
            this.hashCode = this.hashCode * 53 + Internal.hashLong(Double.doubleToLongBits(d));
            return d;
        }

        @Override
        public Internal.DoubleList visitDoubleList(Internal.DoubleList doubleList, Internal.DoubleList doubleList2) {
            this.hashCode = this.hashCode * 53 + doubleList.hashCode();
            return doubleList;
        }

        @Override
        public FieldSet<ExtensionDescriptor> visitExtensions(FieldSet<ExtensionDescriptor> fieldSet, FieldSet<ExtensionDescriptor> fieldSet2) {
            this.hashCode = this.hashCode * 53 + fieldSet.hashCode();
            return fieldSet;
        }

        @Override
        public float visitFloat(boolean bl, float f, boolean bl2, float f2) {
            this.hashCode = this.hashCode * 53 + Float.floatToIntBits(f);
            return f;
        }

        @Override
        public Internal.FloatList visitFloatList(Internal.FloatList floatList, Internal.FloatList floatList2) {
            this.hashCode = this.hashCode * 53 + floatList.hashCode();
            return floatList;
        }

        @Override
        public int visitInt(boolean bl, int n, boolean bl2, int n2) {
            this.hashCode = this.hashCode * 53 + n;
            return n;
        }

        @Override
        public Internal.IntList visitIntList(Internal.IntList intList, Internal.IntList intList2) {
            this.hashCode = this.hashCode * 53 + intList.hashCode();
            return intList;
        }

        @Override
        public LazyFieldLite visitLazyMessage(boolean bl, LazyFieldLite lazyFieldLite, boolean bl2, LazyFieldLite lazyFieldLite2) {
            this.hashCode = this.hashCode * 53 + lazyFieldLite.hashCode();
            return lazyFieldLite;
        }

        @Override
        public <T> Internal.ProtobufList<T> visitList(Internal.ProtobufList<T> protobufList, Internal.ProtobufList<T> protobufList2) {
            this.hashCode = this.hashCode * 53 + protobufList.hashCode();
            return protobufList;
        }

        @Override
        public long visitLong(boolean bl, long l, boolean bl2, long l2) {
            this.hashCode = this.hashCode * 53 + Internal.hashLong(l);
            return l;
        }

        @Override
        public Internal.LongList visitLongList(Internal.LongList longList, Internal.LongList longList2) {
            this.hashCode = this.hashCode * 53 + longList.hashCode();
            return longList;
        }

        @Override
        public <K, V> MapFieldLite<K, V> visitMap(MapFieldLite<K, V> mapFieldLite, MapFieldLite<K, V> mapFieldLite2) {
            this.hashCode = this.hashCode * 53 + mapFieldLite.hashCode();
            return mapFieldLite;
        }

        @Override
        public <T extends MessageLite> T visitMessage(T t, T t2) {
            int n = t != null ? (t instanceof GeneratedMessageLite ? ((GeneratedMessageLite)t).hashCode(this) : t.hashCode()) : 37;
            this.hashCode = this.hashCode * 53 + n;
            return t;
        }

        @Override
        public Object visitOneofBoolean(boolean bl, Object object, Object object2) {
            this.hashCode = this.hashCode * 53 + Internal.hashBoolean((Boolean)object);
            return object;
        }

        @Override
        public Object visitOneofByteString(boolean bl, Object object, Object object2) {
            this.hashCode = this.hashCode * 53 + object.hashCode();
            return object;
        }

        @Override
        public Object visitOneofDouble(boolean bl, Object object, Object object2) {
            this.hashCode = this.hashCode * 53 + Internal.hashLong(Double.doubleToLongBits((Double)object));
            return object;
        }

        @Override
        public Object visitOneofFloat(boolean bl, Object object, Object object2) {
            this.hashCode = this.hashCode * 53 + Float.floatToIntBits(((Float)object).floatValue());
            return object;
        }

        @Override
        public Object visitOneofInt(boolean bl, Object object, Object object2) {
            this.hashCode = this.hashCode * 53 + (Integer)object;
            return object;
        }

        @Override
        public Object visitOneofLazyMessage(boolean bl, Object object, Object object2) {
            this.hashCode = this.hashCode * 53 + object.hashCode();
            return object;
        }

        @Override
        public Object visitOneofLong(boolean bl, Object object, Object object2) {
            this.hashCode = this.hashCode * 53 + Internal.hashLong((Long)object);
            return object;
        }

        @Override
        public Object visitOneofMessage(boolean bl, Object object, Object object2) {
            return this.visitMessage((MessageLite)object, (MessageLite)object2);
        }

        @Override
        public void visitOneofNotSet(boolean bl) {
            if (!bl) {
                return;
            }
            throw new IllegalStateException();
        }

        @Override
        public Object visitOneofString(boolean bl, Object object, Object object2) {
            this.hashCode = this.hashCode * 53 + object.hashCode();
            return object;
        }

        @Override
        public String visitString(boolean bl, String string2, boolean bl2, String string3) {
            this.hashCode = this.hashCode * 53 + string2.hashCode();
            return string2;
        }

        @Override
        public UnknownFieldSetLite visitUnknownFields(UnknownFieldSetLite unknownFieldSetLite, UnknownFieldSetLite unknownFieldSetLite2) {
            this.hashCode = this.hashCode * 53 + unknownFieldSetLite.hashCode();
            return unknownFieldSetLite;
        }
    }

    protected static class MergeFromVisitor
    implements Visitor {
        public static final MergeFromVisitor INSTANCE = new MergeFromVisitor();

        private MergeFromVisitor() {
        }

        @Override
        public boolean visitBoolean(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
            block0 : {
                if (!bl3) break block0;
                bl2 = bl4;
            }
            return bl2;
        }

        @Override
        public Internal.BooleanList visitBooleanList(Internal.BooleanList booleanList, Internal.BooleanList booleanList2) {
            int n = booleanList.size();
            int n2 = booleanList2.size();
            Internal.BooleanList booleanList3 = booleanList;
            if (n > 0) {
                booleanList3 = booleanList;
                if (n2 > 0) {
                    booleanList3 = booleanList;
                    if (!booleanList.isModifiable()) {
                        booleanList3 = booleanList.mutableCopyWithCapacity(n + n2);
                    }
                    booleanList3.addAll(booleanList2);
                }
            }
            booleanList = n > 0 ? booleanList3 : booleanList2;
            return booleanList;
        }

        @Override
        public ByteString visitByteString(boolean bl, ByteString byteString, boolean bl2, ByteString byteString2) {
            if (!bl2) {
                byteString2 = byteString;
            }
            return byteString2;
        }

        @Override
        public double visitDouble(boolean bl, double d, boolean bl2, double d2) {
            block0 : {
                if (!bl2) break block0;
                d = d2;
            }
            return d;
        }

        @Override
        public Internal.DoubleList visitDoubleList(Internal.DoubleList doubleList, Internal.DoubleList doubleList2) {
            int n = doubleList.size();
            int n2 = doubleList2.size();
            Internal.DoubleList doubleList3 = doubleList;
            if (n > 0) {
                doubleList3 = doubleList;
                if (n2 > 0) {
                    doubleList3 = doubleList;
                    if (!doubleList.isModifiable()) {
                        doubleList3 = doubleList.mutableCopyWithCapacity(n + n2);
                    }
                    doubleList3.addAll(doubleList2);
                }
            }
            doubleList = n > 0 ? doubleList3 : doubleList2;
            return doubleList;
        }

        @Override
        public FieldSet<ExtensionDescriptor> visitExtensions(FieldSet<ExtensionDescriptor> fieldSet, FieldSet<ExtensionDescriptor> fieldSet2) {
            Object object = fieldSet;
            if (fieldSet.isImmutable()) {
                object = fieldSet.clone();
            }
            ((FieldSet)object).mergeFrom(fieldSet2);
            return object;
        }

        @Override
        public float visitFloat(boolean bl, float f, boolean bl2, float f2) {
            block0 : {
                if (!bl2) break block0;
                f = f2;
            }
            return f;
        }

        @Override
        public Internal.FloatList visitFloatList(Internal.FloatList floatList, Internal.FloatList floatList2) {
            int n = floatList.size();
            int n2 = floatList2.size();
            Internal.FloatList floatList3 = floatList;
            if (n > 0) {
                floatList3 = floatList;
                if (n2 > 0) {
                    floatList3 = floatList;
                    if (!floatList.isModifiable()) {
                        floatList3 = floatList.mutableCopyWithCapacity(n + n2);
                    }
                    floatList3.addAll(floatList2);
                }
            }
            if (n <= 0) {
                floatList3 = floatList2;
            }
            return floatList3;
        }

        @Override
        public int visitInt(boolean bl, int n, boolean bl2, int n2) {
            block0 : {
                if (!bl2) break block0;
                n = n2;
            }
            return n;
        }

        @Override
        public Internal.IntList visitIntList(Internal.IntList intList, Internal.IntList intList2) {
            block3 : {
                int n = intList.size();
                int n2 = intList2.size();
                Internal.IntList intList3 = intList;
                if (n > 0) {
                    intList3 = intList;
                    if (n2 > 0) {
                        intList3 = intList;
                        if (!intList.isModifiable()) {
                            intList3 = intList.mutableCopyWithCapacity(n + n2);
                        }
                        intList3.addAll(intList2);
                    }
                }
                if (n <= 0) break block3;
                intList2 = intList3;
            }
            return intList2;
        }

        @Override
        public LazyFieldLite visitLazyMessage(boolean bl, LazyFieldLite lazyFieldLite, boolean bl2, LazyFieldLite lazyFieldLite2) {
            lazyFieldLite.merge(lazyFieldLite2);
            return lazyFieldLite;
        }

        @Override
        public <T> Internal.ProtobufList<T> visitList(Internal.ProtobufList<T> protobufList, Internal.ProtobufList<T> protobufList2) {
            block3 : {
                int n = protobufList.size();
                int n2 = protobufList2.size();
                Internal.ProtobufList<T> protobufList3 = protobufList;
                if (n > 0) {
                    protobufList3 = protobufList;
                    if (n2 > 0) {
                        protobufList3 = protobufList;
                        if (!protobufList.isModifiable()) {
                            protobufList3 = protobufList.mutableCopyWithCapacity(n + n2);
                        }
                        protobufList3.addAll(protobufList2);
                    }
                }
                if (n <= 0) break block3;
                protobufList2 = protobufList3;
            }
            return protobufList2;
        }

        @Override
        public long visitLong(boolean bl, long l, boolean bl2, long l2) {
            block0 : {
                if (!bl2) break block0;
                l = l2;
            }
            return l;
        }

        @Override
        public Internal.LongList visitLongList(Internal.LongList longList, Internal.LongList longList2) {
            block3 : {
                int n = longList.size();
                int n2 = longList2.size();
                Internal.LongList longList3 = longList;
                if (n > 0) {
                    longList3 = longList;
                    if (n2 > 0) {
                        longList3 = longList;
                        if (!longList.isModifiable()) {
                            longList3 = longList.mutableCopyWithCapacity(n + n2);
                        }
                        longList3.addAll(longList2);
                    }
                }
                if (n <= 0) break block3;
                longList2 = longList3;
            }
            return longList2;
        }

        @Override
        public <K, V> MapFieldLite<K, V> visitMap(MapFieldLite<K, V> mapFieldLite, MapFieldLite<K, V> mapFieldLite2) {
            mapFieldLite.mergeFrom(mapFieldLite2);
            return mapFieldLite;
        }

        @Override
        public <T extends MessageLite> T visitMessage(T t, T t2) {
            block1 : {
                if (t != null && t2 != null) {
                    return (T)t.toBuilder().mergeFrom(t2).build();
                }
                if (t == null) break block1;
                t2 = t;
            }
            return t2;
        }

        @Override
        public Object visitOneofBoolean(boolean bl, Object object, Object object2) {
            return object2;
        }

        @Override
        public Object visitOneofByteString(boolean bl, Object object, Object object2) {
            return object2;
        }

        @Override
        public Object visitOneofDouble(boolean bl, Object object, Object object2) {
            return object2;
        }

        @Override
        public Object visitOneofFloat(boolean bl, Object object, Object object2) {
            return object2;
        }

        @Override
        public Object visitOneofInt(boolean bl, Object object, Object object2) {
            return object2;
        }

        @Override
        public Object visitOneofLazyMessage(boolean bl, Object object, Object object2) {
            if (bl) {
                object = (LazyFieldLite)object;
                ((LazyFieldLite)object).merge((LazyFieldLite)object2);
                return object;
            }
            return object2;
        }

        @Override
        public Object visitOneofLong(boolean bl, Object object, Object object2) {
            return object2;
        }

        @Override
        public Object visitOneofMessage(boolean bl, Object object, Object object2) {
            if (bl) {
                return this.visitMessage((MessageLite)object, (MessageLite)object2);
            }
            return object2;
        }

        @Override
        public void visitOneofNotSet(boolean bl) {
        }

        @Override
        public Object visitOneofString(boolean bl, Object object, Object object2) {
            return object2;
        }

        @Override
        public String visitString(boolean bl, String string2, boolean bl2, String string3) {
            block0 : {
                if (!bl2) break block0;
                string2 = string3;
            }
            return string2;
        }

        @Override
        public UnknownFieldSetLite visitUnknownFields(UnknownFieldSetLite unknownFieldSetLite, UnknownFieldSetLite unknownFieldSetLite2) {
            if (unknownFieldSetLite2 != UnknownFieldSetLite.getDefaultInstance()) {
                unknownFieldSetLite = UnknownFieldSetLite.mutableCopyOf(unknownFieldSetLite, unknownFieldSetLite2);
            }
            return unknownFieldSetLite;
        }
    }

    public static enum MethodToInvoke {
        IS_INITIALIZED,
        VISIT,
        MERGE_FROM_STREAM,
        MAKE_IMMUTABLE,
        NEW_MUTABLE_INSTANCE,
        NEW_BUILDER,
        GET_DEFAULT_INSTANCE,
        GET_PARSER;
        
    }

    protected static final class SerializedForm
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final byte[] asBytes;
        private final String messageClassName;

        SerializedForm(MessageLite messageLite) {
            this.messageClassName = messageLite.getClass().getName();
            this.asBytes = messageLite.toByteArray();
        }

        public static SerializedForm of(MessageLite messageLite) {
            return new SerializedForm(messageLite);
        }

        protected Object readResolve() throws ObjectStreamException {
            try {
                Object object = Class.forName(this.messageClassName).getDeclaredField("DEFAULT_INSTANCE");
                ((AccessibleObject)object).setAccessible(true);
                object = ((MessageLite)((Field)object).get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
                return object;
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw new RuntimeException("Unable to understand proto buffer", invalidProtocolBufferException);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException("Unable to call parsePartialFrom", illegalAccessException);
            }
            catch (SecurityException securityException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to call DEFAULT_INSTANCE in ");
                stringBuilder.append(this.messageClassName);
                throw new RuntimeException(stringBuilder.toString(), securityException);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to find DEFAULT_INSTANCE in ");
                stringBuilder.append(this.messageClassName);
                throw new RuntimeException(stringBuilder.toString(), noSuchFieldException);
            }
            catch (ClassNotFoundException classNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to find proto buffer class: ");
                stringBuilder.append(this.messageClassName);
                throw new RuntimeException(stringBuilder.toString(), classNotFoundException);
            }
        }
    }

    protected static interface Visitor {
        public boolean visitBoolean(boolean var1, boolean var2, boolean var3, boolean var4);

        public Internal.BooleanList visitBooleanList(Internal.BooleanList var1, Internal.BooleanList var2);

        public ByteString visitByteString(boolean var1, ByteString var2, boolean var3, ByteString var4);

        public double visitDouble(boolean var1, double var2, boolean var4, double var5);

        public Internal.DoubleList visitDoubleList(Internal.DoubleList var1, Internal.DoubleList var2);

        public FieldSet<ExtensionDescriptor> visitExtensions(FieldSet<ExtensionDescriptor> var1, FieldSet<ExtensionDescriptor> var2);

        public float visitFloat(boolean var1, float var2, boolean var3, float var4);

        public Internal.FloatList visitFloatList(Internal.FloatList var1, Internal.FloatList var2);

        public int visitInt(boolean var1, int var2, boolean var3, int var4);

        public Internal.IntList visitIntList(Internal.IntList var1, Internal.IntList var2);

        public LazyFieldLite visitLazyMessage(boolean var1, LazyFieldLite var2, boolean var3, LazyFieldLite var4);

        public <T> Internal.ProtobufList<T> visitList(Internal.ProtobufList<T> var1, Internal.ProtobufList<T> var2);

        public long visitLong(boolean var1, long var2, boolean var4, long var5);

        public Internal.LongList visitLongList(Internal.LongList var1, Internal.LongList var2);

        public <K, V> MapFieldLite<K, V> visitMap(MapFieldLite<K, V> var1, MapFieldLite<K, V> var2);

        public <T extends MessageLite> T visitMessage(T var1, T var2);

        public Object visitOneofBoolean(boolean var1, Object var2, Object var3);

        public Object visitOneofByteString(boolean var1, Object var2, Object var3);

        public Object visitOneofDouble(boolean var1, Object var2, Object var3);

        public Object visitOneofFloat(boolean var1, Object var2, Object var3);

        public Object visitOneofInt(boolean var1, Object var2, Object var3);

        public Object visitOneofLazyMessage(boolean var1, Object var2, Object var3);

        public Object visitOneofLong(boolean var1, Object var2, Object var3);

        public Object visitOneofMessage(boolean var1, Object var2, Object var3);

        public void visitOneofNotSet(boolean var1);

        public Object visitOneofString(boolean var1, Object var2, Object var3);

        public String visitString(boolean var1, String var2, boolean var3, String var4);

        public UnknownFieldSetLite visitUnknownFields(UnknownFieldSetLite var1, UnknownFieldSetLite var2);
    }

}

