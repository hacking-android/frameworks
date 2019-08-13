/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.CodedOutputStream;
import com.android.framework.protobuf.Internal;
import com.android.framework.protobuf.LazyField;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.SmallSortedMap;
import com.android.framework.protobuf.WireFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class FieldSet<FieldDescriptorType extends FieldDescriptorLite<FieldDescriptorType>> {
    private static final FieldSet DEFAULT_INSTANCE = new FieldSet<FieldDescriptorType>(true);
    private final SmallSortedMap<FieldDescriptorType, Object> fields;
    private boolean hasLazyField = false;
    private boolean isImmutable;

    private FieldSet() {
        this.fields = SmallSortedMap.newFieldMap(16);
    }

    private FieldSet(boolean bl) {
        this.fields = SmallSortedMap.newFieldMap(0);
        this.makeImmutable();
    }

    private void cloneFieldEntry(Map<FieldDescriptorType, Object> map, Map.Entry<FieldDescriptorType, Object> object) {
        FieldDescriptorLite fieldDescriptorLite = (FieldDescriptorLite)object.getKey();
        if ((object = object.getValue()) instanceof LazyField) {
            map.put(fieldDescriptorLite, ((LazyField)object).getValue());
        } else {
            map.put(fieldDescriptorLite, object);
        }
    }

    private Object cloneIfMutable(Object arrby) {
        if (arrby instanceof byte[]) {
            byte[] arrby2 = arrby;
            arrby = new byte[arrby2.length];
            System.arraycopy(arrby2, 0, arrby, 0, arrby2.length);
            return arrby;
        }
        return arrby;
    }

    private static int computeElementSize(WireFormat.FieldType fieldType, int n, Object object) {
        int n2;
        n = n2 = CodedOutputStream.computeTagSize(n);
        if (fieldType == WireFormat.FieldType.GROUP) {
            n = n2 * 2;
        }
        return FieldSet.computeElementSizeNoTag(fieldType, object) + n;
    }

    static int computeElementSizeNoTag(WireFormat.FieldType fieldType, Object object) {
        switch (fieldType) {
            default: {
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
            }
            case ENUM: {
                if (object instanceof Internal.EnumLite) {
                    return CodedOutputStream.computeEnumSizeNoTag(((Internal.EnumLite)object).getNumber());
                }
                return CodedOutputStream.computeEnumSizeNoTag((Integer)object);
            }
            case SINT64: {
                return CodedOutputStream.computeSInt64SizeNoTag((Long)object);
            }
            case SINT32: {
                return CodedOutputStream.computeSInt32SizeNoTag((Integer)object);
            }
            case SFIXED64: {
                return CodedOutputStream.computeSFixed64SizeNoTag((Long)object);
            }
            case SFIXED32: {
                return CodedOutputStream.computeSFixed32SizeNoTag((Integer)object);
            }
            case UINT32: {
                return CodedOutputStream.computeUInt32SizeNoTag((Integer)object);
            }
            case BYTES: {
                if (object instanceof ByteString) {
                    return CodedOutputStream.computeBytesSizeNoTag((ByteString)object);
                }
                return CodedOutputStream.computeByteArraySizeNoTag((byte[])object);
            }
            case STRING: {
                if (object instanceof ByteString) {
                    return CodedOutputStream.computeBytesSizeNoTag((ByteString)object);
                }
                return CodedOutputStream.computeStringSizeNoTag((String)object);
            }
            case MESSAGE: {
                if (object instanceof LazyField) {
                    return CodedOutputStream.computeLazyFieldSizeNoTag((LazyField)object);
                }
                return CodedOutputStream.computeMessageSizeNoTag((MessageLite)object);
            }
            case GROUP: {
                return CodedOutputStream.computeGroupSizeNoTag((MessageLite)object);
            }
            case BOOL: {
                return CodedOutputStream.computeBoolSizeNoTag((Boolean)object);
            }
            case FIXED32: {
                return CodedOutputStream.computeFixed32SizeNoTag((Integer)object);
            }
            case FIXED64: {
                return CodedOutputStream.computeFixed64SizeNoTag((Long)object);
            }
            case INT32: {
                return CodedOutputStream.computeInt32SizeNoTag((Integer)object);
            }
            case UINT64: {
                return CodedOutputStream.computeUInt64SizeNoTag((Long)object);
            }
            case INT64: {
                return CodedOutputStream.computeInt64SizeNoTag((Long)object);
            }
            case FLOAT: {
                return CodedOutputStream.computeFloatSizeNoTag(((Float)object).floatValue());
            }
            case DOUBLE: 
        }
        return CodedOutputStream.computeDoubleSizeNoTag((Double)object);
    }

    public static int computeFieldSize(FieldDescriptorLite<?> iterator, Object object) {
        WireFormat.FieldType fieldType = iterator.getLiteType();
        int n = iterator.getNumber();
        if (iterator.isRepeated()) {
            if (iterator.isPacked()) {
                int n2 = 0;
                iterator = ((List)object).iterator();
                while (iterator.hasNext()) {
                    n2 += FieldSet.computeElementSizeNoTag(fieldType, iterator.next());
                }
                return CodedOutputStream.computeTagSize(n) + n2 + CodedOutputStream.computeRawVarint32Size(n2);
            }
            int n3 = 0;
            iterator = ((List)object).iterator();
            while (iterator.hasNext()) {
                n3 += FieldSet.computeElementSize(fieldType, n, iterator.next());
            }
            return n3;
        }
        return FieldSet.computeElementSize(fieldType, n, object);
    }

    public static <T extends FieldDescriptorLite<T>> FieldSet<T> emptySet() {
        return DEFAULT_INSTANCE;
    }

    private int getMessageSetSerializedSize(Map.Entry<FieldDescriptorType, Object> entry) {
        FieldDescriptorLite fieldDescriptorLite = (FieldDescriptorLite)entry.getKey();
        Object object = entry.getValue();
        if (fieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !fieldDescriptorLite.isRepeated() && !fieldDescriptorLite.isPacked()) {
            if (object instanceof LazyField) {
                return CodedOutputStream.computeLazyFieldMessageSetExtensionSize(((FieldDescriptorLite)entry.getKey()).getNumber(), (LazyField)object);
            }
            return CodedOutputStream.computeMessageSetExtensionSize(((FieldDescriptorLite)entry.getKey()).getNumber(), (MessageLite)object);
        }
        return FieldSet.computeFieldSize(fieldDescriptorLite, object);
    }

    static int getWireFormatForFieldType(WireFormat.FieldType fieldType, boolean bl) {
        if (bl) {
            return 2;
        }
        return fieldType.getWireType();
    }

    private boolean isInitialized(Map.Entry<FieldDescriptorType, Object> iterator) {
        FieldDescriptorLite fieldDescriptorLite = (FieldDescriptorLite)iterator.getKey();
        if (fieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            if (fieldDescriptorLite.isRepeated()) {
                iterator = ((List)iterator.getValue()).iterator();
                while (iterator.hasNext()) {
                    if (((MessageLite)iterator.next()).isInitialized()) continue;
                    return false;
                }
            } else if ((iterator = iterator.getValue()) instanceof MessageLite) {
                if (!((MessageLite)((Object)iterator)).isInitialized()) {
                    return false;
                }
            } else {
                if (iterator instanceof LazyField) {
                    return true;
                }
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
        }
        return true;
    }

    private void mergeFromField(Map.Entry<FieldDescriptorType, Object> object3) {
        FieldDescriptorLite fieldDescriptorLite = (FieldDescriptorLite)object3.getKey();
        Object object2 = object3.getValue();
        object3 = object2;
        if (object2 instanceof LazyField) {
            object3 = ((LazyField)object2).getValue();
        }
        if (fieldDescriptorLite.isRepeated()) {
            Iterator iterator = this.getField(fieldDescriptorLite);
            object2 = iterator;
            if (iterator == null) {
                object2 = new ArrayList();
            }
            for (Object object3 : (List)object3) {
                ((List)object2).add(this.cloneIfMutable(object3));
            }
            this.fields.put((FieldDescriptorType)fieldDescriptorLite, object2);
        } else if (fieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            object2 = this.getField(fieldDescriptorLite);
            if (object2 == null) {
                this.fields.put((FieldDescriptorType)fieldDescriptorLite, this.cloneIfMutable(object3));
            } else {
                object3 = fieldDescriptorLite.internalMergeFrom(((MessageLite)object2).toBuilder(), (MessageLite)object3).build();
                this.fields.put((FieldDescriptorType)fieldDescriptorLite, object3);
            }
        } else {
            this.fields.put((FieldDescriptorType)fieldDescriptorLite, this.cloneIfMutable(object3));
        }
    }

    public static <T extends FieldDescriptorLite<T>> FieldSet<T> newFieldSet() {
        return new FieldSet<FieldDescriptorType>();
    }

    public static Object readPrimitiveField(CodedInputStream codedInputStream, WireFormat.FieldType fieldType, boolean bl) throws IOException {
        if (bl) {
            return WireFormat.readPrimitiveField(codedInputStream, fieldType, WireFormat.Utf8Validation.STRICT);
        }
        return WireFormat.readPrimitiveField(codedInputStream, fieldType, WireFormat.Utf8Validation.LOOSE);
    }

    private static void verifyType(WireFormat.FieldType fieldType, Object object) {
        if (object != null) {
            boolean bl = false;
            int n = 1.$SwitchMap$com$google$protobuf$WireFormat$JavaType[fieldType.getJavaType().ordinal()];
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            switch (n) {
                default: {
                    bl4 = bl;
                    break;
                }
                case 9: {
                    if (!(object instanceof MessageLite) && !(object instanceof LazyField)) break;
                    bl4 = true;
                    break;
                }
                case 8: {
                    if (!(object instanceof Integer)) {
                        bl4 = bl2;
                        if (!(object instanceof Internal.EnumLite)) break;
                    }
                    bl4 = true;
                    break;
                }
                case 7: {
                    if (!(object instanceof ByteString)) {
                        bl4 = bl3;
                        if (!(object instanceof byte[])) break;
                    }
                    bl4 = true;
                    break;
                }
                case 6: {
                    bl4 = object instanceof String;
                    break;
                }
                case 5: {
                    bl4 = object instanceof Boolean;
                    break;
                }
                case 4: {
                    bl4 = object instanceof Double;
                    break;
                }
                case 3: {
                    bl4 = object instanceof Float;
                    break;
                }
                case 2: {
                    bl4 = object instanceof Long;
                    break;
                }
                case 1: {
                    bl4 = object instanceof Integer;
                }
            }
            if (bl4) {
                return;
            }
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        throw new NullPointerException();
    }

    private static void writeElement(CodedOutputStream codedOutputStream, WireFormat.FieldType fieldType, int n, Object object) throws IOException {
        if (fieldType == WireFormat.FieldType.GROUP) {
            codedOutputStream.writeGroup(n, (MessageLite)object);
        } else {
            codedOutputStream.writeTag(n, FieldSet.getWireFormatForFieldType(fieldType, false));
            FieldSet.writeElementNoTag(codedOutputStream, fieldType, object);
        }
    }

    static void writeElementNoTag(CodedOutputStream codedOutputStream, WireFormat.FieldType fieldType, Object object) throws IOException {
        switch (fieldType) {
            default: {
                break;
            }
            case ENUM: {
                if (object instanceof Internal.EnumLite) {
                    codedOutputStream.writeEnumNoTag(((Internal.EnumLite)object).getNumber());
                    break;
                }
                codedOutputStream.writeEnumNoTag((Integer)object);
                break;
            }
            case SINT64: {
                codedOutputStream.writeSInt64NoTag((Long)object);
                break;
            }
            case SINT32: {
                codedOutputStream.writeSInt32NoTag((Integer)object);
                break;
            }
            case SFIXED64: {
                codedOutputStream.writeSFixed64NoTag((Long)object);
                break;
            }
            case SFIXED32: {
                codedOutputStream.writeSFixed32NoTag((Integer)object);
                break;
            }
            case UINT32: {
                codedOutputStream.writeUInt32NoTag((Integer)object);
                break;
            }
            case BYTES: {
                if (object instanceof ByteString) {
                    codedOutputStream.writeBytesNoTag((ByteString)object);
                    break;
                }
                codedOutputStream.writeByteArrayNoTag((byte[])object);
                break;
            }
            case STRING: {
                if (object instanceof ByteString) {
                    codedOutputStream.writeBytesNoTag((ByteString)object);
                    break;
                }
                codedOutputStream.writeStringNoTag((String)object);
                break;
            }
            case MESSAGE: {
                codedOutputStream.writeMessageNoTag((MessageLite)object);
                break;
            }
            case GROUP: {
                codedOutputStream.writeGroupNoTag((MessageLite)object);
                break;
            }
            case BOOL: {
                codedOutputStream.writeBoolNoTag((Boolean)object);
                break;
            }
            case FIXED32: {
                codedOutputStream.writeFixed32NoTag((Integer)object);
                break;
            }
            case FIXED64: {
                codedOutputStream.writeFixed64NoTag((Long)object);
                break;
            }
            case INT32: {
                codedOutputStream.writeInt32NoTag((Integer)object);
                break;
            }
            case UINT64: {
                codedOutputStream.writeUInt64NoTag((Long)object);
                break;
            }
            case INT64: {
                codedOutputStream.writeInt64NoTag((Long)object);
                break;
            }
            case FLOAT: {
                codedOutputStream.writeFloatNoTag(((Float)object).floatValue());
                break;
            }
            case DOUBLE: {
                codedOutputStream.writeDoubleNoTag((Double)object);
            }
        }
    }

    public static void writeField(FieldDescriptorLite<?> iterator, Object object, CodedOutputStream codedOutputStream) throws IOException {
        WireFormat.FieldType fieldType = iterator.getLiteType();
        int n = iterator.getNumber();
        if (iterator.isRepeated()) {
            object = (List)object;
            if (iterator.isPacked()) {
                codedOutputStream.writeTag(n, 2);
                n = 0;
                iterator = object.iterator();
                while (iterator.hasNext()) {
                    n += FieldSet.computeElementSizeNoTag(fieldType, iterator.next());
                }
                codedOutputStream.writeRawVarint32(n);
                iterator = object.iterator();
                while (iterator.hasNext()) {
                    FieldSet.writeElementNoTag(codedOutputStream, fieldType, iterator.next());
                }
            } else {
                iterator = object.iterator();
                while (iterator.hasNext()) {
                    FieldSet.writeElement(codedOutputStream, fieldType, n, iterator.next());
                }
            }
        } else if (object instanceof LazyField) {
            FieldSet.writeElement(codedOutputStream, fieldType, n, ((LazyField)object).getValue());
        } else {
            FieldSet.writeElement(codedOutputStream, fieldType, n, object);
        }
    }

    private void writeMessageSetTo(Map.Entry<FieldDescriptorType, Object> entry, CodedOutputStream codedOutputStream) throws IOException {
        Object object = (FieldDescriptorLite)entry.getKey();
        if (object.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !object.isRepeated() && !object.isPacked()) {
            Object object2;
            object = object2 = entry.getValue();
            if (object2 instanceof LazyField) {
                object = ((LazyField)object2).getValue();
            }
            codedOutputStream.writeMessageSetExtension(((FieldDescriptorLite)entry.getKey()).getNumber(), (MessageLite)object);
        } else {
            FieldSet.writeField(object, entry.getValue(), codedOutputStream);
        }
    }

    public void addRepeatedField(FieldDescriptorType object, Object object2) {
        if (object.isRepeated()) {
            FieldSet.verifyType(object.getLiteType(), object2);
            ArrayList arrayList = this.getField(object);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.fields.put((FieldDescriptorType)object, (Object)arrayList);
                object = arrayList;
            } else {
                object = arrayList;
            }
            object.add((Object)object2);
            return;
        }
        throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
    }

    public void clear() {
        this.fields.clear();
        this.hasLazyField = false;
    }

    public void clearField(FieldDescriptorType FieldDescriptorType) {
        this.fields.remove(FieldDescriptorType);
        if (this.fields.isEmpty()) {
            this.hasLazyField = false;
        }
    }

    public FieldSet<FieldDescriptorType> clone() {
        FieldSet<T> fieldSet = FieldSet.newFieldSet();
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            Map.Entry<FieldDescriptorType, Object> entry = this.fields.getArrayEntryAt(i);
            fieldSet.setField((FieldDescriptorLite)entry.getKey(), entry.getValue());
        }
        for (Map.Entry entry : this.fields.getOverflowEntries()) {
            fieldSet.setField((FieldDescriptorLite)entry.getKey(), entry.getValue());
        }
        fieldSet.hasLazyField = this.hasLazyField;
        return fieldSet;
    }

    public boolean equals(Object smallSortedMap) {
        if (this == smallSortedMap) {
            return true;
        }
        if (!(smallSortedMap instanceof FieldSet)) {
            return false;
        }
        smallSortedMap = ((FieldSet)smallSortedMap).fields;
        return smallSortedMap.equals(smallSortedMap);
    }

    public Map<FieldDescriptorType, Object> getAllFields() {
        if (this.hasLazyField) {
            SmallSortedMap smallSortedMap = SmallSortedMap.newFieldMap(16);
            for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
                this.cloneFieldEntry(smallSortedMap, this.fields.getArrayEntryAt(i));
            }
            Iterator<Map.Entry<FieldDescriptorType, Object>> iterator = this.fields.getOverflowEntries().iterator();
            while (iterator.hasNext()) {
                this.cloneFieldEntry(smallSortedMap, iterator.next());
            }
            if (this.fields.isImmutable()) {
                smallSortedMap.makeImmutable();
            }
            return smallSortedMap;
        }
        Map<FieldDescriptorType, Object> map = this.fields.isImmutable() ? this.fields : Collections.unmodifiableMap(this.fields);
        return map;
    }

    public Object getField(FieldDescriptorType object) {
        if ((object = this.fields.get(object)) instanceof LazyField) {
            return ((LazyField)object).getValue();
        }
        return object;
    }

    public int getMessageSetSerializedSize() {
        int n = 0;
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            n += this.getMessageSetSerializedSize(this.fields.getArrayEntryAt(i));
        }
        Iterator<Map.Entry<FieldDescriptorType, Object>> iterator = this.fields.getOverflowEntries().iterator();
        while (iterator.hasNext()) {
            n += this.getMessageSetSerializedSize(iterator.next());
        }
        return n;
    }

    public Object getRepeatedField(FieldDescriptorType object, int n) {
        if (object.isRepeated()) {
            if ((object = this.getField(object)) != null) {
                return ((List)object).get(n);
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public int getRepeatedFieldCount(FieldDescriptorType object) {
        if (object.isRepeated()) {
            if ((object = this.getField(object)) == null) {
                return 0;
            }
            return ((List)object).size();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public int getSerializedSize() {
        int n = 0;
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            Map.Entry<FieldDescriptorType, Object> entry = this.fields.getArrayEntryAt(i);
            n += FieldSet.computeFieldSize((FieldDescriptorLite)entry.getKey(), entry.getValue());
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.fields.getOverflowEntries()) {
            n += FieldSet.computeFieldSize((FieldDescriptorLite)entry.getKey(), entry.getValue());
        }
        return n;
    }

    public boolean hasField(FieldDescriptorType FieldDescriptorType) {
        if (!FieldDescriptorType.isRepeated()) {
            boolean bl = this.fields.get(FieldDescriptorType) != null;
            return bl;
        }
        throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
    }

    public int hashCode() {
        return this.fields.hashCode();
    }

    public boolean isImmutable() {
        return this.isImmutable;
    }

    public boolean isInitialized() {
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            if (this.isInitialized(this.fields.getArrayEntryAt(i))) continue;
            return false;
        }
        Iterator<Map.Entry<FieldDescriptorType, Object>> iterator = this.fields.getOverflowEntries().iterator();
        while (iterator.hasNext()) {
            if (this.isInitialized(iterator.next())) continue;
            return false;
        }
        return true;
    }

    public Iterator<Map.Entry<FieldDescriptorType, Object>> iterator() {
        if (this.hasLazyField) {
            return new LazyField.LazyIterator(this.fields.entrySet().iterator());
        }
        return this.fields.entrySet().iterator();
    }

    public void makeImmutable() {
        if (this.isImmutable) {
            return;
        }
        this.fields.makeImmutable();
        this.isImmutable = true;
    }

    public void mergeFrom(FieldSet<FieldDescriptorType> object) {
        for (int i = 0; i < ((FieldSet)object).fields.getNumArrayEntries(); ++i) {
            this.mergeFromField(((FieldSet)object).fields.getArrayEntryAt(i));
        }
        object = ((FieldSet)object).fields.getOverflowEntries().iterator();
        while (object.hasNext()) {
            this.mergeFromField((Map.Entry)object.next());
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setField(FieldDescriptorType FieldDescriptorType, Object arrayList) {
        void var2_6;
        if (FieldDescriptorType.isRepeated()) {
            if (!(arrayList instanceof List)) throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            ArrayList arrayList2 = new ArrayList();
            arrayList2.addAll(arrayList);
            for (Object e : arrayList2) {
                FieldSet.verifyType(FieldDescriptorType.getLiteType(), e);
            }
            ArrayList arrayList3 = arrayList2;
        } else {
            FieldSet.verifyType(FieldDescriptorType.getLiteType(), arrayList);
        }
        if (var2_6 instanceof LazyField) {
            this.hasLazyField = true;
        }
        this.fields.put(FieldDescriptorType, (Object)var2_6);
    }

    public void setRepeatedField(FieldDescriptorType FieldDescriptorType, int n, Object object) {
        if (FieldDescriptorType.isRepeated()) {
            Object object2 = this.getField(FieldDescriptorType);
            if (object2 != null) {
                FieldSet.verifyType(FieldDescriptorType.getLiteType(), object);
                ((List)object2).set(n, object);
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public void writeMessageSetTo(CodedOutputStream codedOutputStream) throws IOException {
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            this.writeMessageSetTo(this.fields.getArrayEntryAt(i), codedOutputStream);
        }
        Iterator<Map.Entry<FieldDescriptorType, Object>> iterator = this.fields.getOverflowEntries().iterator();
        while (iterator.hasNext()) {
            this.writeMessageSetTo(iterator.next(), codedOutputStream);
        }
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        for (int i = 0; i < this.fields.getNumArrayEntries(); ++i) {
            Map.Entry<FieldDescriptorType, Object> entry = this.fields.getArrayEntryAt(i);
            FieldSet.writeField((FieldDescriptorLite)entry.getKey(), entry.getValue(), codedOutputStream);
        }
        for (Map.Entry entry : this.fields.getOverflowEntries()) {
            FieldSet.writeField((FieldDescriptorLite)entry.getKey(), entry.getValue(), codedOutputStream);
        }
    }

    public static interface FieldDescriptorLite<T extends FieldDescriptorLite<T>>
    extends Comparable<T> {
        public Internal.EnumLiteMap<?> getEnumType();

        public WireFormat.JavaType getLiteJavaType();

        public WireFormat.FieldType getLiteType();

        public int getNumber();

        public MessageLite.Builder internalMergeFrom(MessageLite.Builder var1, MessageLite var2);

        public boolean isPacked();

        public boolean isRepeated();
    }

}

